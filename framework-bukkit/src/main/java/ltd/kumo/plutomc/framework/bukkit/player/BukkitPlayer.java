package ltd.kumo.plutomc.framework.bukkit.player;

import ltd.kumo.plutomc.framework.shared.player.Player;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public final class BukkitPlayer extends Player<org.bukkit.entity.Player>{

    private BukkitPlayer(@NotNull UUID player) {
        super(player);
    }

    @Override
    public @Nullable org.bukkit.entity.Player player() {
        return Bukkit.getPlayer(uuid());
    }

    public @NotNull Player<org.bukkit.entity.Player> of(org.bukkit.entity.Player player) {
        return new BukkitPlayer(player.getUniqueId());
    }

}
