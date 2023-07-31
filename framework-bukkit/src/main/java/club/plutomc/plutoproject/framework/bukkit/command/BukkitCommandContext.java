package club.plutomc.plutoproject.framework.bukkit.command;

import club.plutomc.plutoproject.framework.bukkit.BukkitPlatform;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import club.plutomc.plutoproject.framework.shared.command.Argument;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;

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
