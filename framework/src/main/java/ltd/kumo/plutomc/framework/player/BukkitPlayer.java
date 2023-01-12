package ltd.kumo.plutomc.framework.player;

import ltd.kumo.plutomc.framework.player.metadata.PlayerMetaContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 代表一个 Bukkit 平台的玩家。
 */
@SuppressWarnings("unused")
public abstract class BukkitPlayer extends AbstractPlayer implements Player {
    @NotNull
    public PlayerMetaContainer<Player> metaContainer() {
        return new PlayerMetaContainer<>(this);
    }
}
