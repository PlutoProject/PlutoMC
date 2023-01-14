package ltd.kumo.plutomc.mainsurvival;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.AbstractPlatform;
import ltd.kumo.plutomc.framework.platforms.BukkitPlatform;
import ltd.kumo.plutomc.mainsurvival.commands.TempCommand;
import ltd.kumo.plutomc.mainsurvival.listeners.PlayerListeners;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 主生存世界插件。
 */
@SuppressWarnings("unused")
public final class MainSurvivalPlugin extends JavaPlugin {
    @NotNull
    private static final ImmutableList<Listener> listeners = ImmutableList.of(
            new PlayerListeners()
    );
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

        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        getServer().getPluginCommand("temp_broadcast_finish").setExecutor(new TempCommand());
    }

    @Override
    public void onDisable() {

    }
}
