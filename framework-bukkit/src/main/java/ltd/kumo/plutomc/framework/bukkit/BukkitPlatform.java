package ltd.kumo.plutomc.framework.bukkit;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BukkitPlatform extends Platform<JavaPlugin> {
    private BukkitPlatform(@NotNull JavaPlugin plugin) {
        super(plugin);
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

    public @NotNull static Platform<JavaPlugin> of(@NotNull JavaPlugin plugin) {
        return new BukkitPlatform(plugin);
    }
}
