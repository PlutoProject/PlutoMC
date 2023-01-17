package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentFloat;


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
