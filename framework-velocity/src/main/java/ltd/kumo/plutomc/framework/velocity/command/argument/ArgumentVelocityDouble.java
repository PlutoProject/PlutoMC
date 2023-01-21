package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;

public class ArgumentVelocityDouble extends ArgumentDouble implements VelocityArgument<Double> {

    @Override
    public Double parse(CommandContext context, String name) {
        return DoubleArgumentType.getDouble(((VelocityCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return DoubleArgumentType.doubleArg();
    }

    public ArgumentType<?> brigadier(double min, double max) {
        return DoubleArgumentType.doubleArg(min, max);
    }


}
