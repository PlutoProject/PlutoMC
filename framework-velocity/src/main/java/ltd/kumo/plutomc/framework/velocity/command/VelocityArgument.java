package ltd.kumo.plutomc.framework.velocity.command;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.shared.command.Argument;

public interface VelocityArgument<T> extends Argument<T> {

    ArgumentType<?> brigadier();

}
