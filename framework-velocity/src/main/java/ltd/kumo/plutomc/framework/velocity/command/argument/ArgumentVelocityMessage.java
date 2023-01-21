package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentMessage;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;
import ltd.kumo.plutomc.framework.velocity.command.brigadier.MessageArgumentType;

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
