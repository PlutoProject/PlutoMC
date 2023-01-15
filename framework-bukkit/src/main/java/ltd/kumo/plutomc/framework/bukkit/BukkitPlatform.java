package ltd.kumo.plutomc.framework.bukkit;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.shared.Platform;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class BukkitPlatform extends Platform<JavaPlugin> {
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
}
