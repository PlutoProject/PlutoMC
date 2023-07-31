package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentString;

public class ArgumentBukkitString extends ArgumentString implements BukkitArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return StringArgumentType.string();
    }

    @Override
    public String parse(CommandContext context, String name) {
        return StringArgumentType.getString(((BukkitCommandContext) context).brigadier(), name);
    }

}
