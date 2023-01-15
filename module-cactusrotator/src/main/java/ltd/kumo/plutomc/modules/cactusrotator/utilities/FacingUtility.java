package ltd.kumo.plutomc.modules.cactusrotator.utilities;

import org.bukkit.block.BlockFace;

public final class FacingUtility {

    public static BlockFace rotate(BlockFace blockFace) {
        return switch (blockFace) {
            case UP -> BlockFace.DOWN;
            case DOWN -> BlockFace.EAST;
            case EAST -> BlockFace.SOUTH;
            case WEST -> BlockFace.NORTH;
            case NORTH -> BlockFace.UP;
            case SOUTH -> BlockFace.WEST;
            default -> null;
        };
    }

    public static BlockFace opposite(BlockFace blockFace) {
        return switch (blockFace) {
            case UP -> BlockFace.DOWN;
            case DOWN -> BlockFace.UP;
            case EAST -> BlockFace.WEST;
            case WEST -> BlockFace.EAST;
            case NORTH -> BlockFace.SOUTH;
            case SOUTH -> BlockFace.NORTH;
            default -> null;
        };
    }

    private FacingUtility() {
    }

}
