package ltd.kumo.plutomc.framework.bukkit.event.async;

import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawClass;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawConstructor;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawField;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.Ref;
import net.deechael.dutil.gson.GsonUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AsyncMessageSendEvent extends Event implements Cancellable {

    private final static RawClass<?> PACKET = RawClass.of(Ref.nmsClass("network.protocol.game.ClientboundSystemChatPacket"));
    private final static RawField ADVENTURE_CONTENT = PACKET.findField(false, Component.class);
    private final static RawField CONTENT = PACKET.findField(false, String.class);
    private final static RawField OVERLAY = PACKET.findField(false, boolean.class);
    private final static RawConstructor<?> CONSTRUCTOR = PACKET.findConstructor(Component.class, String.class, boolean.class);

    private final static GsonComponentSerializer SERIALIZER = GsonComponentSerializer.gson();

    private final static HandlerList handlerList = new HandlerList();

    private final Player receiver;
    private Component content;
    private boolean overlay;


    private boolean cancelled;

    public AsyncMessageSendEvent(Player receiver, Component content, boolean overlay) {
        super(true);
        this.receiver = receiver;
        this.content = content;
        this.overlay = overlay;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Component getContent() {
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setOverlay(boolean overlay) {
        this.overlay = overlay;
    }

    public boolean isOverlay() {
        return overlay;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public static Object handlePacket(Player player, Object packet) {
        if (!PACKET.isInstance(packet))
            return packet;
        Component adventureContent = (Component) ADVENTURE_CONTENT.get(packet);
        String content = (String) CONTENT.get(packet);
        boolean overlay = (boolean) OVERLAY.get(packet);
        Component component = adventureContent != null ? adventureContent : content != null ? SERIALIZER.deserializeFromTree(GsonUtil.getJsonObject(content)) : Component.empty();
        AsyncMessageSendEvent event = new AsyncMessageSendEvent(player, component, overlay);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return null;
        if (event.getContent() == null)
            return null;
        return CONSTRUCTOR.newInstance(event.getContent(), null, event.isOverlay());
    }

}
