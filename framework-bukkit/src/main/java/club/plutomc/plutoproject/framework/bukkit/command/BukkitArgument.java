package club.plutomc.plutoproject.framework.bukkit.command;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.shared.command.Argument;

public interface BukkitArgument<T> extends Argument<T> {

    ArgumentType<?> brigadier();

}
