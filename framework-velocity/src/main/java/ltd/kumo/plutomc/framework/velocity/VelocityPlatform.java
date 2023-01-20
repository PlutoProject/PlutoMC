package ltd.kumo.plutomc.framework.velocity;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public final class VelocityPlatform extends Platform<PluginContainer> {
    @NotNull ProxyServer proxyServer;

    private VelocityPlatform(@NotNull PluginContainer plugin, @NotNull ProxyServer proxyServer) {
        super(plugin);
        Objects.requireNonNull(proxyServer);
        this.proxyServer = proxyServer;
    }

    public static VelocityPlatform of(@NonNull PluginContainer plugin, @NonNull ProxyServer proxyServer) {
        Objects.requireNonNull(plugin);
        Objects.requireNonNull(proxyServer);

        return new VelocityPlatform(plugin, proxyServer);
    }

    @Override
    @NotNull
    public ImmutableList<?> onlinePlayers() {
        return ImmutableList.copyOf(proxyServer.getAllPlayers());
    }

    @Override
    @NotNull
    public String name() {
        return "Velocity";
    }

    @Override
    @NotNull
    public String version() {
        if (plugin().getDescription().getVersion().isEmpty()) {
            return "null";
        }

        return plugin().getDescription().getVersion().get();
    }

    @Override
    public <E extends CommandSender, P extends Player<?>> Command<E, P> createCommand(String name) {
        // TODO
        return null;
    }

    @Override
    public <E extends CommandSender, P extends Player<?>> void registerCommand(String prefix, Command<E, P> command) {
        // TODO
    }

    @Override
    public <E extends Service<E>> E getService(Class<E> clazz) {
        // TODO
        return null;
    }

    @Override
    public void enableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled())
                module.initial();
        });
    }

    @Override
    public void disableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled())
                module.terminate();
        });
    }

    @Override
    public void reloadModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled())
                module.reload();
        });
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
