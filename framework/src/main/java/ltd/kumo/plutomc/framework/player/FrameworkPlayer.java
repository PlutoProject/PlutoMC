package ltd.kumo.plutomc.framework.player;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

public abstract class FrameworkPlayer {
    public static FrameworkPlayer bukkit(@NotNull Player player) {
        return (BukkitPlayer) player;
    }
}
