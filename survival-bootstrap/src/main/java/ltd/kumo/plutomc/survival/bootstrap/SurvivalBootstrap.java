package ltd.kumo.plutomc.survival.bootstrap;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.modules.voidtotem.VoidTotemModule;
import ltd.kumo.plutomc.modules.cactusrotator.CactusRotatorModule;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public class SurvivalBootstrap extends JavaPlugin {

    @Nullable
    private static BukkitPlatform bukkitPlatform;

    public static void reload() {
        Objects.requireNonNull(bukkitPlatform).reloadModules();
    }

    @Nullable
    public static BukkitPlatform bukkitPlatform() {
        return bukkitPlatform;
    }

    @NotNull
    public static SurvivalBootstrap instance() {
        return SurvivalBootstrap.getPlugin(SurvivalBootstrap.class);
    }

    @Override
    public void onEnable() {
        bukkitPlatform = BukkitPlatform.of(this);

        bukkitPlatform.modules(ImmutableList.of(
                new IronElevatorModule(bukkitPlatform),
                new CactusRotatorModule(bukkitPlatform),
                new VoidTotemModule(bukkitPlatform)
        ));

        Objects.requireNonNull(bukkitPlatform).enableModules();
    }

    @Override
    public void onDisable() {
        Objects.requireNonNull(bukkitPlatform).disableModules();
    }
}
