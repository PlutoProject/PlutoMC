package ltd.kumo.plutomc.framework.bukkit.holograms;

import lombok.Getter;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.animations.AnimationManager;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.Hologram;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.HologramManager;
import ltd.kumo.plutomc.framework.bukkit.holograms.nms.NMS;
import ltd.kumo.plutomc.framework.bukkit.holograms.nms.PacketListener;
import ltd.kumo.plutomc.framework.bukkit.holograms.player.PlayerListener;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.BungeeUtils;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.Common;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.PExecutor;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.reflect.ReflectionUtil;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.reflect.Version;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.tick.Ticker;
import ltd.kumo.plutomc.framework.bukkit.holograms.world.WorldListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

@Getter
public final class PlutoHolograms {

    private final JavaPlugin plugin;
    private HologramManager hologramManager;
    private AnimationManager animationManager;
    private PacketListener packetListener;
    private Ticker ticker;
    private File dataFolder;
    private boolean updateAvailable;

    /*
     *	Constructors
     */

    PlutoHolograms(@NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /*
     *	General Methods
     */

    void load() {
        // Check if NMS version is supported
        if (Version.CURRENT == null) {
            Common.log(Level.SEVERE, "Unsupported server version: " + ReflectionUtil.getVersion());
            Common.log(Level.SEVERE, "Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    void enable() {
        NMS.init();
        PExecutor.init(3);

        this.ticker = new Ticker();
        this.hologramManager = new HologramManager();
        this.animationManager = new AnimationManager();
        this.packetListener = new PacketListener();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this.plugin);
        pm.registerEvents(new WorldListener(), this.plugin);

        BungeeUtils.init();
    }

    void disable() {
        this.packetListener.destroy();
        this.hologramManager.destroy();
        this.animationManager.destroy();
        this.ticker.destroy();

        for (Hologram hologram : Hologram.getCachedHolograms()) {
            hologram.destroy();
        }

        BungeeUtils.destroy();
        PExecutor.shutdown();
    }

    /**
     * Reload the plugin
     */
    public void reload() {

        this.animationManager.reload();
        this.hologramManager.reload();
    }

    /**
     * Get the data folder for DecentHolograms files.
     *
     * @return the file.
     */
    public File getDataFolder() {
        if (this.dataFolder == null) {
            this.dataFolder = new File("plugins/DecentHolograms");
        }
        return this.dataFolder;
    }

}
