package ltd.kumo.plutomc.framework.bukkit;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandManager;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.listeners.CommandListeners;
import ltd.kumo.plutomc.framework.bukkit.services.HologramService;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "unchecked"})
public class BukkitPlatform extends Platform<JavaPlugin> {

    private final Map<Class<? extends Service<?>>, Service<?>> services = new HashMap<>();
    private BukkitCommandManager commandManager;

    private BukkitPlatform(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    public @NotNull
    static BukkitPlatform of(@NotNull JavaPlugin plugin) {
        Objects.requireNonNull(plugin);
        return new BukkitPlatform(plugin);
    }

    @Override
    public @NotNull ImmutableList<?> onlinePlayers() {
        return ImmutableList.copyOf(plugin().getServer().getOnlinePlayers());
    }

    @Override
    public @NotNull String name() {
        return "Bukkit";
    }

    @Override
    public @NotNull String version() {
        return plugin().getServer().getVersion();
    }

    @Override
    public BukkitCommand createCommand(String name) {
        return new BukkitCommand(this, name, false);
    }

    @Override
    public <E extends CommandSender, P extends Player<?>> void registerCommand(String prefix, Command<E, P> command) {
        this.commandManager.register(prefix, (BukkitCommand) command);
    }

    @Override
    public <E extends Service<E>> E getService(Class<E> clazz) {
        return (E) this.services.get(clazz);
    }

    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public void enableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.initial();
            }
        });
    }

    @Override
    public void disableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.terminate();
            }
        });
    }

    @Override
    public void reloadModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.reload();
            }
        });
    }

    @Override
    public void load() {
        PlutoHologramsAPI.onLoad(this.plugin());
    }

    @Override
    public void enable() {
        this.commandManager = new BukkitCommandManager(this);
        PlutoHologramsAPI.onEnable();
        this.services.put(HologramService.class, new HologramService());
        Bukkit.getPluginManager().registerEvents(new CommandListeners(this), this.plugin());
    }

    @Override
    public void disable() {
        PlutoHologramsAPI.onDisable();
    }

}
