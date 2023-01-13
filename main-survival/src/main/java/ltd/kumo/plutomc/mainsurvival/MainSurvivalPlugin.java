package ltd.kumo.plutomc.mainsurvival;

import ltd.kumo.plutomc.framework.AbstractPlatform;
import ltd.kumo.plutomc.framework.platforms.BukkitPlatform;
import ltd.kumo.plutomc.mainsurvival.commands.TempCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

/**
 * 主生存世界插件。
 */
@SuppressWarnings("unused")
public final class MainSurvivalPlugin extends JavaPlugin {
    @Nullable
    private static JavaPlugin instance;

    @Nullable
    private static BukkitPlatform platform;

    @Nullable
    public static BukkitPlatform getPlatform() {
        return platform;
    }

    @Nullable
    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        platform = (BukkitPlatform) AbstractPlatform.bukkit(getServer());

        getServer().getPluginCommand("temp_broadcast_finish").setExecutor(new TempCommand());
    }

    @Override
    public void onDisable() {

    }
}
