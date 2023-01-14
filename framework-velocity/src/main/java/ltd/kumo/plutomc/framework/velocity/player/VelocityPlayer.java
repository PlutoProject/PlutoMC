package ltd.kumo.plutomc.framework.velocity.player;

import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public final class VelocityPlayer extends Player<com.velocitypowered.api.proxy.Player> {
    @NotNull ProxyServer proxyServer;

    private VelocityPlayer(@NotNull com.velocitypowered.api.proxy.Player player, @NotNull ProxyServer proxyServer) {
        super(player.getUniqueId());
        Objects.requireNonNull(proxyServer);
        this.proxyServer = proxyServer;
    }

    @Nullable
    @Override
    public com.velocitypowered.api.proxy.Player player() {
        if (proxyServer.getPlayer(uuid()).isPresent()) {
            return proxyServer.getPlayer(uuid()).get();
        }

        return null;
    }

    public @NotNull Player<com.velocitypowered.api.proxy.Player> of(@NotNull com.velocitypowered.api.proxy.Player player, @NotNull ProxyServer proxyServer) {
        return new VelocityPlayer(player, proxyServer);
    }
}
