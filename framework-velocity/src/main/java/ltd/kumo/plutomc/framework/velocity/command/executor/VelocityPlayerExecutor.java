package ltd.kumo.plutomc.framework.velocity.command.executor;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import ltd.kumo.plutomc.framework.shared.player.Player;
import ltd.kumo.plutomc.framework.velocity.player.VelocityPlayer;

@FunctionalInterface
public interface VelocityPlayerExecutor extends PlayerExecutor {

    @Override
    default void executes(Player<?> sender, CommandContext context) throws CommandSyntaxException {
        this.executes((VelocityPlayer) sender, context);
    }

    void executes(VelocityPlayer player, CommandContext context) throws CommandSyntaxException;

}
