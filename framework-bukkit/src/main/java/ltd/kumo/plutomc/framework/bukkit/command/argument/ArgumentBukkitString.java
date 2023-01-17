package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentString;

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
