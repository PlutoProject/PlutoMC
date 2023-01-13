package ltd.kumo.plutomc.framework.player;

import ltd.kumo.plutomc.framework.metadata.player.PlayerMetaContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 代表一个 Bukkit 平台的玩家。
 */
@SuppressWarnings("unused")
public abstract class BukkitPlayer extends AbstractPlayer implements Player {
    public static AbstractPlayer of(@NotNull Player player) {
        return (BukkitPlayer) player;
    }

    @NotNull
    @Override
    public PlayerMetaContainer<Player> metaContainer() {
        return new PlayerMetaContainer<>(this);
    }
}
