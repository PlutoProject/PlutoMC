package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentLong;

public class ArgumentVelocityLong extends ArgumentLong implements VelocityArgument<Long> {

    @Override
    public Long parse(CommandContext context, String name) {
        return LongArgumentType.getLong(((VelocityCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return LongArgumentType.longArg();
    }

    public ArgumentType<?> brigadier(long min, long max) {
        return LongArgumentType.longArg(min, max);
    }

}
