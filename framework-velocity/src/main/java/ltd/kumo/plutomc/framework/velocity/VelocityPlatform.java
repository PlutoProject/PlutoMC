package ltd.kumo.plutomc.framework.velocity;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public final class VelocityPlatform extends Platform<Plugin> {
    @NotNull ProxyServer proxyServer;

    private VelocityPlatform(@NotNull Plugin plugin, @NotNull ProxyServer proxyServer) {
        super(plugin);
        Objects.requireNonNull(proxyServer);
        this.proxyServer = proxyServer;
    }

    @Override
    public @NotNull ImmutableList<?> onlinePlayers() {
        return ImmutableList.copyOf(proxyServer.getAllPlayers());
    }

    @Override
    public @NotNull String name() {
        return "Velocity";
    }

    @Override
    public @NotNull String version() {
        return plugin().version();
    }

    @Override
    public void enableModules() {
        modules().forEach(Module::initial);
    }

    @Override
    public void disableModules() {
        modules().forEach(Module::terminate);
    }

    @Override
    public void reloadModules() {
        modules().forEach(Module::reload);
    }

    public static Platform<Plugin> of(@NonNull Plugin plugin, @NonNull ProxyServer proxyServer) {
        Objects.requireNonNull(plugin);
        Objects.requireNonNull(proxyServer);

        return new VelocityPlatform(plugin, proxyServer);
    }
}
