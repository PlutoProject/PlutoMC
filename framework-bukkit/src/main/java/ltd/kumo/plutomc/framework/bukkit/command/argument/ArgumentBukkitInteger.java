package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;

public class ArgumentBukkitInteger extends ArgumentInteger implements BukkitArgument<Integer> {

    @Override
    public Integer parse(CommandContext context, String name) {
        return IntegerArgumentType.getInteger(((BukkitCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return IntegerArgumentType.integer();
    }

    public ArgumentType<?> brigadier(int min, int max) {
        return IntegerArgumentType.integer(min, max);
    }

}
