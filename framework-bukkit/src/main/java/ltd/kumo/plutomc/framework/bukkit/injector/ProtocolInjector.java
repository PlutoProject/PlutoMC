package ltd.kumo.plutomc.framework.bukkit.injector;

import io.netty.channel.*;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.event.async.AsyncMessageSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProtocolInjector {

    private final BukkitPlatform platform;
    private final String identifier;

    private final List<?> networkManagers;

    private final Iterable<?> pendingNetworkManagers;

    private final EventListener listener = new EventListener();
    private final AtomicBoolean closed = new AtomicBoolean(false);

    private final Map<UUID, Player> playerCache = Collections.synchronizedMap(new HashMap<>());
    private final Set<Channel> injectedChannels = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    public ProtocolInjector(BukkitPlatform platform) {
        if (!Bukkit.isPrimaryThread())
            throw new IllegalStateException("ProtocolInjector must be constructed on the main thread.");


        this.platform = platform;
        this.identifier = "plutomc-protocol-injector";

        Object conn = ProtocolReflections.FIELD_SERVER_CONNECTION.get(ProtocolReflections.METHOD_GET_SERVER.invoke(Bukkit.getServer()));
        if (conn == null)
            throw new RuntimeException("[ProtocolInjector] ServerConnection is null.");
        networkManagers = (List<?>) ProtocolReflections.FIELD_NETWORK_MANAGERS_LIST.get(conn);
        pendingNetworkManagers = (Iterable<?>) ProtocolReflections.FIELD_PENDING_NETWORK_MANAGERS.get(conn);

        Bukkit.getPluginManager().registerEvents(listener, this.platform.plugin());

        // Inject already online players
        for (Player p : Bukkit.getOnlinePlayers())
            injectPlayer(p);
    }

    protected Object onPacketReceiveAsync(Player sender, Channel channel, Object packet) {
        return packet;
    }

    protected Object onPacketSendAsync(Player receiver, Channel channel, Object packet) {
        packet = AsyncMessageSendEvent.handlePacket(receiver, packet);
        return packet;
    }

    public final void sendPacket(Player receiver, Object packet) {
        Objects.requireNonNull(receiver, "Player is null.");
        Objects.requireNonNull(packet, "Packet is null.");
        sendPacket(getChannel(receiver), packet);
    }

    public final void sendPacket(Channel channel, Object packet) {
        Objects.requireNonNull(channel, "Channel is null.");
        Objects.requireNonNull(packet, "Packet is null.");
        channel.pipeline().writeAndFlush(packet);
    }

    public final void receivePacket(Player sender, Object packet) {
        Objects.requireNonNull(sender, "Player is null.");
        Objects.requireNonNull(packet, "Packet is null.");
        receivePacket(getChannel(sender), packet);
    }

    public final void receivePacket(Channel channel, Object packet) {
        Objects.requireNonNull(channel, "Channel is null.");
        Objects.requireNonNull(packet, "Packet is null.");
        ChannelHandlerContext encoder = channel.pipeline().context("encoder");
        Objects.requireNonNull(encoder, "Channel is not a player channel").fireChannelRead(packet);
    }

    public final void close() {
        if (closed.getAndSet(true)) {
            return;
        }

        listener.unregister();

        synchronized (networkManagers) { // Lock out Minecraft
            for (Object manager : networkManagers) {
                Channel channel = getChannel(manager);
                channel.eventLoop().submit(() -> channel.pipeline().remove(identifier));
            }
        }

        playerCache.clear();
        injectedChannels.clear();
    }

    public final boolean isClosed() {
        return closed.get();
    }

    private void injectPlayer(Player player) {
        injectChannel(getChannel(player)).player = player;
    }

    private PacketHandler injectChannel(Channel channel) {
        PacketHandler handler = new PacketHandler();

        channel.eventLoop().submit(() -> {
            if (isClosed())
                return;
            if (injectedChannels.add(channel))
                channel.pipeline().addBefore("packet_handler", identifier, handler);
        });

        return handler;
    }

    private void injectNetworkManager(Object networkManager) {
        Channel channel = getChannel(networkManager);
        if (injectedChannels.contains(channel))
            return;
        injectChannel(channel);
    }

    private Object getNetworkManager(Player player) {
        return ProtocolReflections.FIELD_NETWORK_MANAGER
                .get(ProtocolReflections.FIELD_PLAYER_CONNECTION
                        .get(ProtocolReflections.METHOD_GET_HANDLE.invoke(player)));
    }

    private Channel getChannel(Player player) {
        return getChannel(getNetworkManager(player));
    }

    private Channel getChannel(Object networkManager) {
        return (Channel) ProtocolReflections.FIELD_CHANNEL.get(networkManager);
    }


    private final class EventListener implements Listener {

        @EventHandler(priority = EventPriority.LOWEST)
        private void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
            if (isClosed())
                return;

            synchronized (networkManagers) {
                if (networkManagers instanceof RandomAccess) {
                    for (int i = networkManagers.size() - 1; i >= 0; i--) {
                        Object networkManager = networkManagers.get(i);
                        injectNetworkManager(networkManager);
                    }
                } else {
                    for (Object networkManager : networkManagers)
                        injectNetworkManager(networkManager);
                }

                synchronized (pendingNetworkManagers) {
                    for (Object networkManager : pendingNetworkManagers)
                        injectNetworkManager(networkManager);
                }
            }
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onPlayerLoginEvent(PlayerLoginEvent event) {
            if (isClosed()) {
                return;
            }

            playerCache.put(event.getPlayer().getUniqueId(), event.getPlayer());
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onPlayerJoinEvent(PlayerJoinEvent event) {
            if (isClosed()) {
                return;
            }
            Player player = event.getPlayer();

            Object networkManager = getNetworkManager(player);
            Channel channel = getChannel(networkManager);
            ChannelHandler channelHandler = channel.pipeline().get(identifier);
            if (channelHandler != null) {
                if (channelHandler instanceof PacketHandler) {
                    ((PacketHandler) channelHandler).player = player;

                    playerCache.remove(player.getUniqueId());
                }
                return;
            }

            injectChannel(channel).player = player;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        private void onPluginDisableEvent(PluginDisableEvent event) {
            if (platform.plugin().equals(event.getPlugin())) {
                close();
            }
        }

        private void unregister() {
            AsyncPlayerPreLoginEvent.getHandlerList().unregister(this);
            PlayerLoginEvent.getHandlerList().unregister(this);
            PlayerJoinEvent.getHandlerList().unregister(this);
            PluginDisableEvent.getHandlerList().unregister(this);
        }

    }


    private final class PacketHandler extends ChannelDuplexHandler {
        private volatile Player player;

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            injectedChannels.remove(ctx.channel());

            super.channelUnregistered(ctx);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
            if (player == null && ProtocolReflections.CLASS_PACKET_LOGIN_OUT_SUCCESS.isInstance(packet)) {
                Player player = playerCache.remove((UUID) ProtocolReflections.METHOD_GET_ID.invoke(ProtocolReflections.FIELD_GAME_PROFILE.get(packet)));

                if (player != null)
                    this.player = player;
            }

            Object newPacket;
            try {
                newPacket = onPacketSendAsync(player, ctx.channel(), packet);
            } catch (OutOfMemoryError error) {
                throw error;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                super.write(ctx, packet, promise);
                return;
            }
            if (newPacket != null)
                super.write(ctx, newPacket, promise);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
            Object newPacket;
            try {
                newPacket = onPacketReceiveAsync(player, ctx.channel(), packet);
            } catch (OutOfMemoryError error) {
                throw error;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                super.channelRead(ctx, packet);
                return;
            }
            if (newPacket != null)
                super.channelRead(ctx, newPacket);
        }
    }

}
