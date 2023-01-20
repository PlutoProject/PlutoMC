package ltd.kumo.plutomc.framework.velocity.modules;

import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class VelocityModule extends Module {
    public VelocityModule(@NotNull Platform<?> platform) {
        super(platform);
    }

    @Override
    public @NotNull Logger logger() {
        return Logger.getLogger("VelocityPlatform - " + name());
    }

    public <T> void listener(@NotNull ProxyServer proxyServer, @NotNull T listener) {
        Objects.requireNonNull(proxyServer);
        Objects.requireNonNull(listener);

        proxyServer.getEventManager().register(platform.plugin(), listener);
    }

    public void command(@NotNull ProxyServer proxyServer, @NotNull CommandMeta commandMeta, @NotNull Command command) {
        Objects.requireNonNull(proxyServer);
        Objects.requireNonNull(command);
        Objects.requireNonNull(command);

        proxyServer.getCommandManager().register(commandMeta, command);
    }

    public void command(@NotNull ProxyServer proxyServer, @NotNull String alias, @NotNull Command command, @NotNull String... otherAlias) {
        Objects.requireNonNull(proxyServer);
        Objects.requireNonNull(command);
        Objects.requireNonNull(command);
        Objects.requireNonNull(otherAlias);

        proxyServer.getCommandManager().register(alias, command, otherAlias);
    }

    public void command(@NotNull ProxyServer proxyServer, @NotNull BrigadierCommand command) {
        Objects.requireNonNull(proxyServer);
        Objects.requireNonNull(command);

        proxyServer.getCommandManager().register(command);
    }
}
