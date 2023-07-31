package club.plutomc.plutoproject.framework.shared.command.executors;

import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandSender;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface Executor {

    void executes(CommandSender sender, CommandContext context) throws CommandSyntaxException;

}
