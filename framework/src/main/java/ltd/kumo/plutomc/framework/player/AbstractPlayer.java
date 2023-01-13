package ltd.kumo.plutomc.framework.player;

import ltd.kumo.plutomc.framework.metadata.AbstractMetaContainer;
import ltd.kumo.plutomc.framework.metadata.player.PlayerMetaContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 框架玩家。
 */
@SuppressWarnings("unused")
public abstract class AbstractPlayer {
    public static AbstractPlayer bukkit(@NotNull Player player) {
        return (BukkitPlayer) player;
    }

    public static AbstractPlayer velocity(@NotNull com.velocitypowered.api.proxy.Player player) {
        return new ProxyPlayer(player);
    }

    @NotNull
    public abstract PlayerMetaContainer<?> metaContainer();
}
