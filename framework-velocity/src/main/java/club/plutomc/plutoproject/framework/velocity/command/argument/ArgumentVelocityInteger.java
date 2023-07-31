package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentInteger;

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
