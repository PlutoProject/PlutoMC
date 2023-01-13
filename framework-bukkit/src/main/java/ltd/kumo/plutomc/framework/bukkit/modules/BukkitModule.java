package ltd.kumo.plutomc.framework.bukkit.modules;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitModule extends Module {
    public BukkitModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
    }

    public <T> void listener(Listener listener) {
        ((JavaPlugin) platform.plugin()).getServer().getPluginManager().registerEvents(listener, ((JavaPlugin) platform.plugin()));
    }

    public <T> void command(String name, T command) {
        if (command instanceof TabExecutor tabExecutor) {
            ((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name).setExecutor(tabExecutor);
            ((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name).setTabCompleter(tabExecutor);
        } else if (command instanceof CommandExecutor commandExecutor) {
            ((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name).setExecutor(commandExecutor);
        }
    }
}
