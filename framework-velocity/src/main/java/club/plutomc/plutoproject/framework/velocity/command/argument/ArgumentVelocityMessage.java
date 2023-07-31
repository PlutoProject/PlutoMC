package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;
import club.plutomc.plutoproject.framework.velocity.command.brigadier.MessageArgumentType;
import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentMessage;

public class ArgumentVelocityMessage extends ArgumentMessage implements VelocityArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return MessageArgumentType.message();
    }

    @Override
    public String parse(CommandContext context, String name) {
        return MessageArgumentType.getMessage(((VelocityCommandContext) context).brigadier(), name);
    }

}
