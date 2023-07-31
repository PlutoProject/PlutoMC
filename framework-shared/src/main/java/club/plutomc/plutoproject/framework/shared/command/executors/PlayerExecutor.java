package club.plutomc.plutoproject.framework.shared.command.executors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.player.Player;

@FunctionalInterface
public interface PlayerExecutor {

    void executes(Player<?> player, CommandContext context) throws CommandSyntaxException;

}
