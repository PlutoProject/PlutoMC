package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;

public class BukkitCommandContext implements CommandContext {

    private final static DynamicCommandExceptionType errorThrower = new DynamicCommandExceptionType(message -> new LiteralMessage("" + message));

    private final BukkitPlatform platform;
    private final com.mojang.brigadier.context.CommandContext<?> brigadier;

    public BukkitCommandContext(BukkitPlatform platform, com.mojang.brigadier.context.CommandContext<?> context) {
        this.platform = platform;
        this.brigadier = context;
    }

    @Override
    public <T extends Argument<E>, E> E argument(Class<T> type, String name) {
        BukkitArgument<E> argument = platform.getCommandManager().argument(type);
        return argument.parse(this, name);
    }

    @Override
    public void error(String message) throws CommandSyntaxException {
        throw errorThrower.create(message);
    }

    public com.mojang.brigadier.context.CommandContext<?> brigadier() {
        return brigadier;
    }

}
