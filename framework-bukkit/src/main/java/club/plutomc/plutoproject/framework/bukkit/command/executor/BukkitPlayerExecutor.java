package club.plutomc.plutoproject.framework.bukkit.command.executor;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.executors.PlayerExecutor;
import club.plutomc.plutoproject.framework.shared.player.Player;

@FunctionalInterface
public interface BukkitPlayerExecutor extends PlayerExecutor {

    @Override
    default void executes(Player<?> sender, CommandContext context) throws CommandSyntaxException {
        this.executes((BukkitPlayer) sender, context);
    }

    void executes(BukkitPlayer player, CommandContext context) throws CommandSyntaxException;

}
