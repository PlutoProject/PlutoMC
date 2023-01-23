package ltd.kumo.plutomc.framework.shared.command.executors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;

@FunctionalInterface
public interface Executor {

    void executes(CommandSender sender, CommandContext context) throws CommandSyntaxException;

}
