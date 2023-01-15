package ltd.kumo.plutomc.survival.bootstrap;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public class SurvivalBootstrap extends JavaPlugin {

    @Nullable
    public static JavaPlugin instance;
    @Nullable
    private static BukkitPlatform bukkitPlatform;

    @Override
    public void onEnable() {
        instance = this;
        bukkitPlatform = BukkitPlatform.of(this);

        bukkitPlatform.modules(ImmutableList.of(
                new IronElevatorModule(bukkitPlatform)
        ));

        Objects.requireNonNull(bukkitPlatform).enableModules();
    }

    @Override
    public void onDisable() {
        Objects.requireNonNull(bukkitPlatform).disableModules();
    }

    public static void reload() {
        Objects.requireNonNull(bukkitPlatform).reloadModules();
    }

    @Nullable
    public static BukkitPlatform bukkitPlatform() {
        return bukkitPlatform;
    }

    @Nullable
    public static JavaPlugin instance() {
        return instance;
    }
}
