package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitDouble;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitFloat;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitInteger;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitLong;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.Suggestion;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentFloat;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentLong;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BukkitCommand implements Command<BukkitCommandSender, BukkitPlayer> {

    private final BukkitPlatform platform;
    private final String name;
    private BiConsumer<BukkitCommandSender, CommandContext> executor;
    private BiConsumer<BukkitPlayer, CommandContext> executorPlayer;
    private Consumer<Suggestion> suggestion;
    private Predicate<BukkitCommandSender> requirement;
    private final boolean argument;
    private final BukkitArgument<?> argumentType;
    private final ArgumentType<?> brigadierType;

    private final List<BukkitCommand> children = new ArrayList<>();

    public BukkitCommand(BukkitPlatform platform, String name) {
        this.platform = platform;
        this.name = name;
        this.argument = false;
        this.argumentType = null;
        this.brigadierType = null;
    }

    public BukkitCommand(BukkitPlatform platform, String name, BukkitArgument<?> argument, ArgumentType<?> brigadierType) {
        this.platform = platform;
        this.name = name;
        this.argumentType = argument;
        this.brigadierType = brigadierType;
        this.argument = argument != null && brigadierType != null;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public BukkitCommand suggests(Consumer<Suggestion> provider) {
        this.suggestion = provider;
        return this;
    }

    @Override
    public BukkitCommand requires(Predicate<BukkitCommandSender> requirement) {
        this.requirement = requirement;
        return this;
    }

    @Override
    public BukkitCommand executes(BiConsumer<BukkitCommandSender, CommandContext> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public BukkitCommand executesPlayer(BiConsumer<BukkitPlayer, CommandContext> executor) {
        this.executorPlayer = executor;
        return this;
    }

    @Override
    public BukkitCommand then(String name) {
        BukkitCommand command = new BukkitCommand(this.platform, name);
        this.children.add(command);
        return command;
    }

    @Override
    public <E extends Argument<A>, A> BukkitCommand then(String name, Class<E> type) {
        BukkitArgument<A> argument = this.platform.getCommandManager().argument(type);
        if (argument == null)
            throw new RuntimeException("Cannot find the argument type");
        BukkitCommand command = new BukkitCommand(this.platform, name, argument, argument.brigadier());
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenInteger(String name, int min, int max) {
        ArgumentBukkitInteger argument = (ArgumentBukkitInteger) this.platform.getCommandManager().argument(ArgumentInteger.class);
        BukkitCommand command = new BukkitCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenLong(String name, long min, long max) {
        ArgumentBukkitLong argument = (ArgumentBukkitLong) this.platform.getCommandManager().argument(ArgumentLong.class);
        BukkitCommand command = new BukkitCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenFloat(String name, float min, float max) {
        ArgumentBukkitFloat argument = (ArgumentBukkitFloat) this.platform.getCommandManager().argument(ArgumentFloat.class);
        BukkitCommand command = new BukkitCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand thenDouble(String name, double min, double max) {
        ArgumentBukkitDouble argument = (ArgumentBukkitDouble) this.platform.getCommandManager().argument(ArgumentDouble.class);
        BukkitCommand command = new BukkitCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public BukkitCommand clone(String name) {
        BukkitCommand newCommand = new BukkitCommand(this.platform, name, this.argumentType, this.brigadierType);
        newCommand.children.addAll(this.children);
        newCommand.executor = this.executor;
        newCommand.executorPlayer = this.executorPlayer;
        newCommand.suggestion = this.suggestion;
        newCommand.requirement = this.requirement;
        return newCommand;
    }

    @SuppressWarnings("unchecked")
    public ArgumentBuilder<BukkitCommandSender, ?> toBrigadier() {
        ArgumentBuilder<BukkitCommandSender, ?> builder = this.argument ? RequiredArgumentBuilder.argument(this.name, this.brigadierType) : LiteralArgumentBuilder.literal(this.name);
        for (BukkitCommand command : this.children)
            builder.then(command.toBrigadier());
        if (this.requirement != null)
            builder.requires(requirement);
        if (this.suggestion != null && this.argument)
            ((RequiredArgumentBuilder<BukkitCommandSender, ?>) builder).suggests(((commandContext, suggestionsBuilder) -> {
                BukkitSuggestion bukkitSuggestion = new BukkitSuggestion();
                suggestion.accept(bukkitSuggestion);
                bukkitSuggestion.getStringSuggestions().forEach(suggestionsBuilder::suggest);
                bukkitSuggestion.getIntSuggestions().forEach(suggestionsBuilder::suggest);
                return suggestionsBuilder.buildFuture();
            }));
        builder.executes(commandContext -> {
            if (commandContext.getSource() instanceof BukkitPlayer && this.executorPlayer != null)
                this.executorPlayer.accept((BukkitPlayer) commandContext.getSource(), new BukkitCommandContext(this.platform, commandContext));
            else if (this.executor != null)
                this.executor.accept(commandContext.getSource(), new BukkitCommandContext(this.platform, commandContext));
            return 1;
        });
        return builder;
    }

    public ArgumentBuilder<Object, ?> toCommodore() {
        if (this.argument) {
            RequiredArgumentBuilder<Object, ?> requiredArgumentBuilder = RequiredArgumentBuilder.argument(this.name, this.argumentType.commodore());
            for (BukkitCommand command : this.children) {
                requiredArgumentBuilder.then(command.toCommodore());
            }
            if (this.suggestion != null)
                requiredArgumentBuilder.suggests(((commandContext, suggestionsBuilder) -> {
                    BukkitSuggestion bukkitSuggestion = new BukkitSuggestion();
                    suggestion.accept(bukkitSuggestion);
                    bukkitSuggestion.getStringSuggestions().forEach(suggestionsBuilder::suggest);
                    bukkitSuggestion.getIntSuggestions().forEach(suggestionsBuilder::suggest);
                    return suggestionsBuilder.buildFuture();
                }));
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
