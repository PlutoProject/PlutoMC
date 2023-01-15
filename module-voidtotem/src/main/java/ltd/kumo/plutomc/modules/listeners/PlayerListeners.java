package ltd.kumo.plutomc.modules.listeners;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public final class PlayerListeners implements Listener {

    private final Collection<PotionEffect> totemEffects = List.of(
            new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 1),
            new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 40, 0),
            new PotionEffect(PotionEffectType.ABSORPTION, 20 * 5, 1)
    );

    private final HashMap<UUID, Location> lastGroundedLocations = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerMove(PlayerMoveEvent event) {
        if (!event.hasChangedPosition())
            return;
        Location location = event.getTo().clone();
        if (location.subtract(0, 0.05, 0).getBlock().getType().isAir())
            return;
        lastGroundedLocations.put(event.getPlayer().getUniqueId(), event.getTo().toCenterLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {
        lastGroundedLocations.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerDie(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.VOID))
            return;
        Location location = player.getLocation();
        if (location.getY() >= location.getWorld().getMinHeight())
            return;
        if (player.getHealth() - event.getFinalDamage() > 0)
            return;
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)
                && !player.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING))
            return;

        event.setCancelled(true);

        Location safeLocation = lastGroundedLocations.getOrDefault(player.getUniqueId(), location.getWorld().getSpawnLocation());
        Location bedLocation = player.getBedSpawnLocation();
        if (bedLocation != null && Objects.equals(safeLocation.getWorld(), bedLocation.getWorld()))
            safeLocation = bedLocation;
        player.teleportAsync(safeLocation)
                .thenRun(() -> useTotem(player));
    }

    private void useTotem(Player player) {
        ItemStack totem;
        if ((totem = player.getInventory().getItemInMainHand()).getType() != Material.TOTEM_OF_UNDYING &&
                (totem = player.getInventory().getItemInOffHand()).getType() != Material.TOTEM_OF_UNDYING)
            return;

        totem.subtract();

        player.setFallDistance(0);
        player.setHealth(1);
        player.playEffect(EntityEffect.TOTEM_RESURRECT);
        player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.addPotionEffects(this.totemEffects);
    }

}
