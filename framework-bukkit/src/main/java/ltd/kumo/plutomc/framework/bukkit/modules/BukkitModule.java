package ltd.kumo.plutomc.framework.bukkit.modules;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class BukkitModule extends Module {
    public BukkitModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
    }

    public <T> void listener(@NotNull Listener listener) {
        Objects.requireNonNull(listener);
        ((JavaPlugin) platform.plugin()).getServer().getPluginManager().registerEvents(listener, ((JavaPlugin) platform.plugin()));
    }

    public <T> void command(@NotNull String name, @NotNull T command) {
        if (command instanceof TabExecutor tabExecutor) {
            Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setExecutor(tabExecutor);
            Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setTabCompleter(tabExecutor);
        } else if (command instanceof CommandExecutor commandExecutor) {
            Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setExecutor(commandExecutor);
        }
    }

    @Override
    @NotNull
    public Logger logger() {
        return Logger.getLogger("[BukkitPlatform - " + name() + "]");
    }
}
