package ltd.kumo.plutomc.framework.velocity;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import ltd.kumo.plutomc.framework.shared.economy.EconomyService;
import ltd.kumo.plutomc.framework.shared.player.Player;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommand;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandManager;
import ltd.kumo.plutomc.framework.velocity.economy.VelocityEconomyService;
import ltd.kumo.plutomc.framework.velocity.player.VelocityPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public final class VelocityPlatform extends Platform<PluginContainer> {

    private final Map<Class<?>, Service<?>> services = new HashMap<>();
    @NotNull ProxyServer proxyServer;
    private VelocityCommandManager commandManager;

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
    @SuppressWarnings("unchecked")
    public VelocityCommand createCommand(String name) {
        return new VelocityCommand(this, name);
    }

    @Override
    public <E extends CommandSender, P extends Player<?>, X extends PlayerExecutor> void registerCommand(String prefix, Command<E, P, X> command) {
        this.commandManager.register(prefix, (VelocityCommand) command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Service<E>> @NotNull E getService(Class<E> clazz) {
        return Optional.ofNullable((E) this.services.get(clazz)).orElseThrow();
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
        this.commandManager = new VelocityCommandManager(this);

        this.services.put(EconomyService.class, new VelocityEconomyService());
    }

    @Override
    public void disable() {

    }

    @Override
    public @NotNull VelocityPlayer player(UUID uuid) {
        return VelocityPlayer.of(uuid, this);
    }

    public @NotNull ProxyServer getProxyServer() {
        return proxyServer;
    }

    public VelocityCommandManager getCommandManager() {
        return commandManager;
    }

}
