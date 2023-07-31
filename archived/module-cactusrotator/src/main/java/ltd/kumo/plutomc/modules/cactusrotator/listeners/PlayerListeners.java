package ltd.kumo.plutomc.modules.cactusrotator.listeners;

import ltd.kumo.plutomc.modules.cactusrotator.utilities.FacingUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PlayerListeners implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (!player.isSneaking())
            return;
        if (block == null)
            return;
        if (!(block.getBlockData() instanceof Directional directional))
            return;
        Material offhand = player.getInventory().getItemInOffHand().getType();
        if (offhand != Material.CACTUS)
            return;
        event.setCancelled(true);
        if (event.getAction().isLeftClick())
            tryOpposite(block, directional);
        else
            tryRotate(block, directional);
    }

    public void tryOpposite(Block block, Directional directional) {
        BlockFace blockFace = FacingUtility.opposite(directional.getFacing());
        if (blockFace == null)
            return;
        if (!directional.getFaces().contains(blockFace))
            return;
        directional.setFacing(blockFace);
        block.setBlockData(directional);
    }

    public void tryRotate(Block block, Directional directional) {
        BlockFace blockFace = FacingUtility.rotate(directional.getFacing());
        if (blockFace == null)
            return;
        int times = 0;
        while (times < 6 && !directional.getFaces().contains(blockFace)) {
            blockFace = FacingUtility.rotate(blockFace);
            times++;
        }
        if (blockFace == null)
            return;
        if (!directional.getFaces().contains(blockFace))
            return;
        directional.setFacing(blockFace);
        block.setBlockData(directional);
    }

}
