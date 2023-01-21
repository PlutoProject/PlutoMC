package ltd.kumo.plutomc.framework.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;

public class VelocityCommandContext implements CommandContext {

    private final VelocityPlatform platform;
    private final com.mojang.brigadier.context.CommandContext<CommandSource> brigadier;

    public VelocityCommandContext(VelocityPlatform platform, com.mojang.brigadier.context.CommandContext<CommandSource> context) {
        this.platform = platform;
        this.brigadier = context;
    }

    @Override
    public <T extends Argument<E>, E> E argument(Class<T> type, String name) {
        VelocityArgument<E> argument = platform.getCommandManager().argument(type);
        return argument.parse(this, name);
    }

    public com.mojang.brigadier.context.CommandContext<CommandSource> brigadier() {
        return brigadier;
    }

}
