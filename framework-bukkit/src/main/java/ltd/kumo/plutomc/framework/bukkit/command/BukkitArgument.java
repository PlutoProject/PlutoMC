package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.shared.command.Argument;

public interface BukkitArgument<T> extends Argument<T> {

    ArgumentType<?> brigadier();

}
