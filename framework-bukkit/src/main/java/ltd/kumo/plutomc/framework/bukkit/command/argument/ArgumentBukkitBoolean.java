package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentBoolean;

public class ArgumentBukkitBoolean extends ArgumentBoolean implements BukkitArgument<Boolean> {


    @Override
    public ArgumentType<?> brigadier() {
        return BoolArgumentType.bool();
    }

    @Override
    public Boolean parse(CommandContext context, String name) {
        return BoolArgumentType.getBool(((BukkitCommandContext) context).brigadier(), name);
    }

}
