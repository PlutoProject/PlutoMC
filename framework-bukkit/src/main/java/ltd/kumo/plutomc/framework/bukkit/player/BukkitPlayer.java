package ltd.kumo.plutomc.framework.bukkit.player;

import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandSender;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public final class BukkitPlayer extends BukkitCommandSender implements Player<org.bukkit.entity.Player> {

    private final UUID player;

    private BukkitPlayer(@NotNull UUID player) {
        this.player = player;
    }

    public static @NotNull BukkitPlayer of(org.bukkit.entity.Player player) {
        return new BukkitPlayer(player.getUniqueId());
    }

    @Override
    public @Nullable org.bukkit.entity.Player player() {
        return Bukkit.getPlayer(uuid());
    }

    @Override
    public @NotNull UUID uuid() {
        return this.player;
    }

    @Override
    public @NotNull String name() {
        org.bukkit.entity.Player player = this.player();
        return player != null ? player.getName() : "null";
    }

    @Override
    public void send(String message) {
        org.bukkit.entity.Player player = this.player();
        if (player == null)
            return;
        player.sendMessage(message);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public CommandSender asBukkit() {
        return this.player();
    }

}
