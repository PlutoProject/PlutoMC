package ltd.kumo.plutomc.framework.player;

import ltd.kumo.plutomc.framework.metadata.player.PlayerMetaContainer;
import org.jetbrains.annotations.NotNull;

/**
 * 框架玩家。
 */
@SuppressWarnings("unused")
public abstract class AbstractPlayer {
    @NotNull
    public abstract PlayerMetaContainer<?> metaContainer();
}
