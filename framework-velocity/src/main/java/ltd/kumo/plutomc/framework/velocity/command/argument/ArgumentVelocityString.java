package ltd.kumo.plutomc.framework.velocity.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentString;
import ltd.kumo.plutomc.framework.velocity.command.VelocityArgument;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommandContext;

public class ArgumentVelocityString extends ArgumentString implements VelocityArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return StringArgumentType.string();
    }

    @Override
    public String parse(CommandContext context, String name) {
        return StringArgumentType.getString(((VelocityCommandContext) context).brigadier(), name);
    }

}
