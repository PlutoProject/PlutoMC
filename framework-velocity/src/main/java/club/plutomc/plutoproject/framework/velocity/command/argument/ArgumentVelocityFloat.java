package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentFloat;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;


public class ArgumentVelocityFloat extends ArgumentFloat implements VelocityArgument<Float> {
    @Override
    public Float parse(CommandContext context, String name) {
        return FloatArgumentType.getFloat(((VelocityCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return FloatArgumentType.floatArg();
    }

    public ArgumentType<?> brigadier(float min, float max) {
        return FloatArgumentType.floatArg(min, max);
    }

}
