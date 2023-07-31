package club.plutomc.plutoproject.framework.velocity.command.executor;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.executors.PlayerExecutor;
import club.plutomc.plutoproject.framework.shared.player.Player;
import club.plutomc.plutoproject.framework.velocity.player.VelocityPlayer;

@FunctionalInterface
public interface VelocityPlayerExecutor extends PlayerExecutor {

    @Override
    default void executes(Player<?> sender, CommandContext context) throws CommandSyntaxException {
        this.executes((VelocityPlayer) sender, context);
    }

    void executes(VelocityPlayer player, CommandContext context) throws CommandSyntaxException;

}
