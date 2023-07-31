package club.plutomc.plutoproject.framework.velocity.command;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.shared.command.Argument;

public interface VelocityArgument<T> extends Argument<T> {

    ArgumentType<?> brigadier();

}
