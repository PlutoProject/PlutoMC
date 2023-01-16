package ltd.kumo.plutomc.framework.bukkit.holograms.world;

import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHolograms;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.DisableCause;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.Hologram;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.HologramManager;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.scheduler.S;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {

    private static final PlutoHolograms PH = PlutoHologramsAPI.get();

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        HologramManager hm = PH.getHologramManager();
        World world = event.getWorld();

        S.async(() -> hm.getHolograms().stream()
                .filter(Hologram::isEnabled)
                .filter(hologram -> hologram.getLocation().getWorld().equals(world))
                .forEach(hologram -> hologram.disable(DisableCause.WORLD_UNLOAD)));
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        HologramManager hm = PH.getHologramManager();
        World world = event.getWorld();

        S.async(() -> {
            hm.getHolograms().stream()
                    .filter(hologram -> !hologram.isEnabled())
                    .filter(hologram -> hologram.getLocation().getWorld().equals(world))
                    .filter(hologram -> hologram.getDisableCause().equals(DisableCause.WORLD_UNLOAD))
                    .forEach(Hologram::enable);
        });
    }

}
