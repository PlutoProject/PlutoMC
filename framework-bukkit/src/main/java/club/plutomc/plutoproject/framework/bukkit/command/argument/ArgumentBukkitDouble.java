package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentDouble;

public class ArgumentBukkitDouble extends ArgumentDouble implements BukkitArgument<Double> {

    @Override
    public Double parse(CommandContext context, String name) {
        return DoubleArgumentType.getDouble(((BukkitCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return DoubleArgumentType.doubleArg();
    }

    public ArgumentType<?> brigadier(double min, double max) {
        return DoubleArgumentType.doubleArg(min, max);
    }


}
