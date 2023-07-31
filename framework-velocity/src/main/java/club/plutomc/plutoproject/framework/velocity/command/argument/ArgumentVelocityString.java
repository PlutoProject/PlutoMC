package club.plutomc.plutoproject.framework.velocity.command.argument;

import club.plutomc.plutoproject.framework.velocity.command.VelocityArgument;
import club.plutomc.plutoproject.framework.velocity.command.VelocityCommandContext;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentString;

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
