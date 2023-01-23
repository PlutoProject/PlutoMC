package ltd.kumo.plutomc.framework.bukkit.command.executor;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import ltd.kumo.plutomc.framework.shared.player.Player;

@FunctionalInterface
public interface BukkitPlayerExecutor extends PlayerExecutor {

    @Override
    default void executes(Player<?> sender, CommandContext context) throws CommandSyntaxException {
        this.executes((BukkitPlayer) sender, context);
    }

    void executes(BukkitPlayer player, CommandContext context) throws CommandSyntaxException;

}
