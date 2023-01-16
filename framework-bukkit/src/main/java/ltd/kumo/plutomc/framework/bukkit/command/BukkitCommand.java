package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitInteger;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BukkitCommand implements Command<BukkitCommandSender, BukkitPlayer> {

    private final BukkitPlatform platform;
    private final String name;
    private final Supplier<ArgumentBuilder<BukkitCommandSender, ?>> supplier;
    private BiConsumer<BukkitCommandSender, CommandContext> executor;
    private BiConsumer<BukkitPlayer, CommandContext> executorPlayer;
    private final boolean argument;

    private final List<BukkitCommand> children = new ArrayList<>();

    public BukkitCommand(BukkitPlatform platform, String name, boolean argument) {
        this(platform, name, () -> LiteralArgumentBuilder.literal(name), argument);
    }

    public BukkitCommand(BukkitPlatform platform, String name, Supplier<ArgumentBuilder<BukkitCommandSender, ?>> supplier, boolean argument) {
        this.platform = platform;
        this.name = name;
        this.supplier = supplier;
        this.argument = argument;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> requires(Predicate<BukkitCommandSender> requirement) {
        return null;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> executes(BiConsumer<BukkitCommandSender, CommandContext> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> executesPlayer(BiConsumer<BukkitPlayer, CommandContext> executor) {
        this.executorPlayer = executor;
        return this;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> then(String name) {
        BukkitCommand command =  new BukkitCommand(this.platform, name, () -> LiteralArgumentBuilder.literal(name), false);
        this.children.add(command);
        return command;
    }

    @Override
    public <E extends Argument<A>, A> Command<BukkitCommandSender, BukkitPlayer> then(String name, Class<E> type) {
        BukkitArgument<A> argument = this.platform.getCommandManager().argument(type);
        if (argument == null)
            throw new RuntimeException("Cannot find the argument type");
        BukkitCommand command = new BukkitCommand(this.platform, name, () -> RequiredArgumentBuilder.argument(name, argument.brigadier()), true);
        this.children.add(command);
        return command;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> thenInteger(String name, int min, int max) {
        ArgumentBukkitInteger argument = (ArgumentBukkitInteger) this.platform.getCommandManager().argument(ArgumentInteger.class);
        BukkitCommand command = new BukkitCommand(this.platform, name, () -> RequiredArgumentBuilder.argument(name, argument.brigadier(min, max)), true);
        this.children.add(command);
        return command;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> thenLong(String name, long min, long max) {
        return null;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> thenFloat(String name, float min, float max) {
        return null;
    }

    @Override
    public Command<BukkitCommandSender, BukkitPlayer> thenDouble(String name, double min, double max) {
        return null;
    }

    @Override
    public BukkitCommand clone(String name) {
        BukkitCommand newCommand = new BukkitCommand(this.platform, name, this.supplier, this.argument);
        newCommand.children.addAll(this.children);
        newCommand.executor = this.executor;
        newCommand.executorPlayer = this.executorPlayer;
        return newCommand;
    }

    public ArgumentBuilder<BukkitCommandSender, ?> toBrigadier() {
        ArgumentBuilder<BukkitCommandSender, ?> builder = this.supplier.get();
        for (BukkitCommand command : this.children)
            builder.then(command.toBrigadier());
        if (this.executor != null)
            builder.executes(commandContext -> {
                this.executor.accept(commandContext.getSource(), new BukkitCommandContext(this.platform, commandContext));
                return 1;
            });
        if (this.executor != null)
            builder.executes(commandContext -> {
                if (commandContext.getSource() instanceof BukkitPlayer)
                    this.executor.accept(commandContext.getSource(), new BukkitCommandContext(this.platform, commandContext));
                return 1;
            });
        return builder;
    }

    public ArgumentBuilder<Object, ?> toCommodore() {
        if (this.argument) {
            RequiredArgumentBuilder<Object, ?> requiredArgumentBuilder = RequiredArgumentBuilder.argument(this.name, ((RequiredArgumentBuilder<?, ?>) this.supplier.get()).getType());
            for (BukkitCommand command : this.children) {
                requiredArgumentBuilder.then(command.toCommodore());
            }
            return requiredArgumentBuilder;
        } else {
            LiteralArgumentBuilder<Object> literalArgumentBuilder = LiteralArgumentBuilder.literal(this.name);
            for (BukkitCommand command : this.children) {
                literalArgumentBuilder.then(command.toCommodore());
            }
            return literalArgumentBuilder;
        }
    }

}
