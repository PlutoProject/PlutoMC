package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentBoolean;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;

public class ArgumentVelocityBoolean extends ArgumentBoolean implements VelocityArgument<Boolean> {


    @Override
    public ArgumentType<?> brigadier() {
        return BoolArgumentType.bool();
    }

    @Override
    public Boolean parse(CommandContext context, String name) {
        return BoolArgumentType.getBool(((VelocityCommandContext) context).brigadier(), name);
    }

}
