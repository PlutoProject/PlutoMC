package ltd.kumo.plutomc.framework.velocity;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
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

    public static VelocityPlatform of(@NonNull Plugin plugin, @NonNull ProxyServer proxyServer) {
        Objects.requireNonNull(plugin);
        Objects.requireNonNull(proxyServer);

        return new VelocityPlatform(plugin, proxyServer);
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
    public <E extends CommandSender> Command<E> createCommand(String name) {
        // TODO
        return null;
    }

    @Override
    public <E extends CommandSender> void registerCommand(String prefix, Command<E> command) {
        // TODO
    }

    @Override
    public <E extends Service<E>> E getService(Class<E> clazz) {
        // TODO
        return null;
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

    @Override
    public void load() {

    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
