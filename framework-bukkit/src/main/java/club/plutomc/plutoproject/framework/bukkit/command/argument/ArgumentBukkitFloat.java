package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentFloat;


public class ArgumentBukkitFloat extends ArgumentFloat implements BukkitArgument<Float> {
    @Override
    public Float parse(CommandContext context, String name) {
        return FloatArgumentType.getFloat(((BukkitCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return FloatArgumentType.floatArg();
    }

    public ArgumentType<?> brigadier(float min, float max) {
        return FloatArgumentType.floatArg(min, max);
    }

}
