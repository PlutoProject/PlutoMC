package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentLong;

public class ArgumentBukkitLong extends ArgumentLong implements BukkitArgument<Long> {

    @Override
    public Long parse(CommandContext context, String name) {
        return LongArgumentType.getLong(((BukkitCommandContext) context).brigadier(), name);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return LongArgumentType.longArg();
    }

    public ArgumentType<?> brigadier(long min, long max) {
        return LongArgumentType.longArg(min, max);
    }

}
