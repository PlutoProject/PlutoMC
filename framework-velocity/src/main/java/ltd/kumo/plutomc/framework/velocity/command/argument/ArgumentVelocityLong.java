package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentLong;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;

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
