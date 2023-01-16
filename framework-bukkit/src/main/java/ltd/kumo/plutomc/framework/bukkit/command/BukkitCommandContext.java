package ltd.kumo.plutomc.framework.bukkit.command;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;

public class BukkitCommandContext implements CommandContext {

    private final BukkitPlatform platform;
    private final com.mojang.brigadier.context.CommandContext<BukkitCommandSender> brigadier;

    public BukkitCommandContext(BukkitPlatform platform, com.mojang.brigadier.context.CommandContext<BukkitCommandSender> context) {
        this.platform = platform;
        this.brigadier = context;
    }

    @Override
    public <T extends Argument<E>, E> E argument(Class<T> type, String name) {
        BukkitArgument<E> argument = platform.getCommandManager().argument(type);
        return argument.parse(this, name);
    }

    public com.mojang.brigadier.context.CommandContext<BukkitCommandSender> brigadier() {
        return brigadier;
    }

}
