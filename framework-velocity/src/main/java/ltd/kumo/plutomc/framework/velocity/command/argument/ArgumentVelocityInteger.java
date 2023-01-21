package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;

public class ArgumentVelocityInteger extends ArgumentInteger implements VelocityArgument<Integer> {

    @Override
    public Integer parse(CommandContext context, String name) {
        return IntegerArgumentType.getInteger(((VelocityCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return IntegerArgumentType.integer();
    }

    public ArgumentType<?> brigadier(int min, int max) {
        return IntegerArgumentType.integer(min, max);
    }

}
