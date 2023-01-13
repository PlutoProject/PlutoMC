package ltd.kumo.plutomc.framework.player;

import ltd.kumo.plutomc.framework.metadata.player.PlayerMetaContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * 代表一个 Bukkit 平台的玩家。
 */
@SuppressWarnings("unused")
public class BukkitPlayer extends AbstractPlayer {
    @NotNull
    private final Player bukkitPlayer;

    public BukkitPlayer(@NotNull Player player) {
        this.bukkitPlayer = player;
    }

    public static AbstractPlayer of(@NotNull Player player) {
        return new BukkitPlayer(player);
    }

    @NotNull
    @Override
    public PlayerMetaContainer<Player> metaContainer() {
        return new PlayerMetaContainer<>(bukkitPlayer);
    }
}
