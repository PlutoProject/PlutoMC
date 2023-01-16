package ltd.kumo.plutomc.modules.waxednotwaxed.listeners;

import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public final class PlayerListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("plutomc.waxednotwaxed.check"))
            return;
        if (!player.isSneaking())
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();
        if (!itemInMainHand.getType().name().toLowerCase().contains("shovel") || block == null)
            return;
        String blockType = block.getType().name().toLowerCase();
        if (!blockType.contains("copper"))
            return;
        if (blockType.startsWith("waxed_")) {
            player.sendActionBar(Component.text("这个方块被打蜡了").color(Catppuccin.MOCHA.GREEN));
            Location location = block.getLocation();
            player.playSound(location, Sound.BLOCK_BEEHIVE_WORK, 0.5f, 1.0f);
            location.getWorld().spawnParticle(Particle.WAX_ON, location.add(.5,.5,.5), 25, 0.3, 0.3, 0.3);
        } else {
            player.sendActionBar(Component.text("这个方块没有被打蜡").color(Catppuccin.MOCHA.MAROON));
            Location location = block.getLocation();
            player.playSound(location, Sound.BLOCK_BEEHIVE_WORK, 0.5f, 1.0f);
            location.getWorld().spawnParticle(Particle.WAX_OFF, location.add(.5,.5,.5), 25, 0.3, 0.3, 0.3);
        }
    }

}
