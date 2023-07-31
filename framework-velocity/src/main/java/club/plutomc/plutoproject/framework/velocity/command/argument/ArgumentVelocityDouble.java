package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentDouble;

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
