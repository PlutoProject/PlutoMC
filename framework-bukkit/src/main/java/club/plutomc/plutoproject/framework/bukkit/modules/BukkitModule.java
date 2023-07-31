package club.plutomc.plutoproject.framework.bukkit.modules;

import club.plutomc.plutoproject.framework.bukkit.BukkitPlatform;
import club.plutomc.plutoproject.framework.shared.modules.Module;
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

    public void listener(@NotNull Listener listener) {
        Objects.requireNonNull(listener);
        ((JavaPlugin) platform.plugin()).getServer().getPluginManager().registerEvents(listener, ((JavaPlugin) platform.plugin()));
    }

    public void command(@NotNull String name, @NotNull CommandExecutor command) {
        Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setExecutor(command);

        if (command instanceof TabExecutor tabExecutor) {
            Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setExecutor(tabExecutor);
            Objects.requireNonNull(((JavaPlugin) platform.plugin()).getServer().getPluginCommand(name)).setTabCompleter(tabExecutor);
        }
    }

    @Override
    @NotNull
    public Logger logger() {
        return Logger.getLogger("BukkitPlatform - " + name());
    }

    @Override
    public @NotNull BukkitPlatform platform() {
        return (BukkitPlatform) this.platform;
    }
}
