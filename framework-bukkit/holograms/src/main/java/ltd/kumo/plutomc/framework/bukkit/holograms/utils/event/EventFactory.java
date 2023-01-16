package ltd.kumo.plutomc.framework.bukkit.holograms.utils.event;

import lombok.experimental.UtilityClass;
import ltd.kumo.plutomc.framework.bukkit.holograms.actions.ClickType;
import ltd.kumo.plutomc.framework.bukkit.holograms.event.HologramClickEvent;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.Hologram;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.HologramPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class EventFactory {

    public static void handleHologramInteractEvent(Player player, Hologram hologram, HologramPage page, ClickType clickType, int entityId) {
        if (HologramClickEvent.getHandlerList().getRegisteredListeners().length == 0) return;

        HologramClickEvent event = new HologramClickEvent(player, hologram, page, clickType, entityId);
        Bukkit.getPluginManager().callEvent(event);
    }

}
