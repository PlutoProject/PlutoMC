package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentFloat;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;


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
