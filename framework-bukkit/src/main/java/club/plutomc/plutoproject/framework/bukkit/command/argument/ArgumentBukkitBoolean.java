package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentBoolean;

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
