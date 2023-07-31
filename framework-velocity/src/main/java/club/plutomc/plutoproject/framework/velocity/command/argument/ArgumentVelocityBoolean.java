package club.plutomc.plutoproject.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentBoolean;
import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;

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
