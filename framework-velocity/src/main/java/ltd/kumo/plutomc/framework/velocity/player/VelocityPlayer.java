package ltd.kumo.plutomc.framework.velocity.player;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.shared.player.Player;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandSender;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public final class VelocityPlayer extends VelocityCommandSender implements Player<com.velocitypowered.api.proxy.Player> {
    private final UUID uuid;
    @NotNull ProxyServer proxyServer;

    private VelocityPlayer(@NotNull com.velocitypowered.api.proxy.Player player, @NotNull VelocityPlatform platform) {
        this(player.getUniqueId(), platform);
    }

    private VelocityPlayer(@NotNull UUID player, @NotNull VelocityPlatform platform) {
        super(platform, platform.getProxyServer().getPlayer(player).orElseThrow());
        Objects.requireNonNull(platform);
        this.proxyServer = platform.getProxyServer();
        this.uuid = player;
    }

    @Nullable
    @Override
    public com.velocitypowered.api.proxy.Player player() {
        if (proxyServer.getPlayer(uuid()).isPresent()) {
            return proxyServer.getPlayer(uuid()).get();
        }
        return null;
    }

    @Override
    public @NotNull UUID uuid() {
        return this.uuid;
    }

    public static @NotNull VelocityPlayer of(@NotNull com.velocitypowered.api.proxy.Player player, @NotNull VelocityPlatform platform) {
        return new VelocityPlayer(player, platform);
    }

    public static @NotNull VelocityPlayer of(@NotNull UUID player, @NotNull VelocityPlatform platform) {
        return new VelocityPlayer(player, platform);
    }

    @Override
    public @NotNull String name() {
        com.velocitypowered.api.proxy.Player player = this.player();
        return player != null ? player.getUsername() : "null";
    }

    @Override
    public void send(String message) {
        com.velocitypowered.api.proxy.Player player = this.player();
        if (player == null)
            return;
        player.sendMessage(Component.text(message));
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public CommandSource asVelocity() {
        return this.player();
    }

}
