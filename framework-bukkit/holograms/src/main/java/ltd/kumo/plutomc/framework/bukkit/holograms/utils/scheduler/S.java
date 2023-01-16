package ltd.kumo.plutomc.framework.bukkit.holograms.utils.scheduler;

import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHolograms;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.PExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitTask;

public class S {

    private static final PlutoHolograms PLUTO_HOLOGRAMS = PlutoHologramsAPI.get();

    public static void stopTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(PLUTO_HOLOGRAMS.getPlugin(), runnable);
    }

    public static BukkitTask sync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(PLUTO_HOLOGRAMS.getPlugin(), runnable, delay);
    }

    public static BukkitTask syncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimer(PLUTO_HOLOGRAMS.getPlugin(), runnable, 0, interval);
    }

    public static void async(Runnable runnable) {
        try {
            Bukkit.getScheduler().runTaskAsynchronously(PLUTO_HOLOGRAMS.getPlugin(), runnable);
        } catch (IllegalPluginAccessException e) {
            PExecutor.execute(runnable);
        }
    }

    public static void async(Runnable runnable, long delay) {
        try {
            Bukkit.getScheduler().runTaskLaterAsynchronously(PLUTO_HOLOGRAMS.getPlugin(), runnable, delay);
        } catch (IllegalPluginAccessException e) {
            PExecutor.execute(runnable);
        }
    }

    public static BukkitTask asyncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(PLUTO_HOLOGRAMS.getPlugin(), runnable, 0, interval);
    }

    public static BukkitTask asyncTask(Runnable runnable, long interval, long delay) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(PLUTO_HOLOGRAMS.getPlugin(), runnable, delay, interval);
    }

}
