package ltd.kumo.plutomc.framework.player;

import com.velocitypowered.api.proxy.Player;
import ltd.kumo.plutomc.framework.metadata.player.PlayerMetaContainer;
import org.jetbrains.annotations.NotNull;

/**
 * 代表一个 of 代理端平台的玩家。
 */
@SuppressWarnings("unused")
public class ProxyPlayer extends AbstractPlayer {
    @NotNull
    Player player;

    public ProxyPlayer(@NotNull Player player) {
        this.player = player;
    }

    public static AbstractPlayer of(@NotNull Player player) {
        return new ProxyPlayer(player);
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    @Override
    public PlayerMetaContainer<Player> metaContainer() {
        return new PlayerMetaContainer<>(player);
    }
}
