package ltd.kumo.plutomc.framework.player;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 框架玩家。
 */
@SuppressWarnings("unused")
public abstract class AbstractPlayer {
    public static AbstractPlayer bukkit(@NotNull Player player) {
        return (BukkitPlayer) player;
    }
}
