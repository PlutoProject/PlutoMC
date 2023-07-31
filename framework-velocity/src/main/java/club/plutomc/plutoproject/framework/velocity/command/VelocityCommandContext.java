package club.plutomc.plutoproject.framework.velocity.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.velocitypowered.api.command.CommandSource;
import club.plutomc.plutoproject.framework.shared.command.Argument;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.velocity.VelocityPlatform;

public class VelocityCommandContext implements CommandContext {

    private final static DynamicCommandExceptionType errorThrower = new DynamicCommandExceptionType(message -> new LiteralMessage("" + message));

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

    @Override
    public void error(String message) throws CommandSyntaxException {
        throw errorThrower.create(message);
    }

    public com.mojang.brigadier.context.CommandContext<CommandSource> brigadier() {
        return brigadier;
    }

}
