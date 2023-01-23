package ltd.kumo.plutomc.framework.velocity.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.Suggestion;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentFloat;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentLong;
import ltd.kumo.plutomc.framework.shared.command.executors.Executor;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.framework.velocity.command.argument.ArgumentVelocityDouble;
import ltd.kumo.plutomc.framework.velocity.command.argument.ArgumentVelocityFloat;
import ltd.kumo.plutomc.framework.velocity.command.argument.ArgumentVelocityInteger;
import ltd.kumo.plutomc.framework.velocity.command.argument.ArgumentVelocityLong;
import ltd.kumo.plutomc.framework.velocity.command.executor.VelocityPlayerExecutor;
import ltd.kumo.plutomc.framework.velocity.command.sender.VelocityCommandSender;
import ltd.kumo.plutomc.framework.velocity.command.sender.VelocityConsoleCommandSender;
import ltd.kumo.plutomc.framework.velocity.player.VelocityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class VelocityCommand implements Command<VelocityCommandSender, VelocityPlayer, VelocityPlayerExecutor> {

    private final VelocityPlatform platform;
    private final String name;
    private final boolean argument;
    private final VelocityArgument<?> argumentType;
    private final ArgumentType<?> brigadierType;
    private final List<VelocityCommand> children = new ArrayList<>();
    private Executor executor;
    private VelocityPlayerExecutor executorPlayer;
    private Consumer<Suggestion> suggestion;
    private Predicate<VelocityCommandSender> requirement;
    private final List<String> aliases = new ArrayList<>();

    public VelocityCommand(VelocityPlatform platform, String name) {
        this.platform = platform;
        this.name = name;
        this.argument = false;
        this.argumentType = null;
        this.brigadierType = null;
    }

    public VelocityCommand(VelocityPlatform platform, String name, VelocityArgument<?> argument, ArgumentType<?> brigadierType) {
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
    public VelocityCommand suggests(Consumer<Suggestion> provider) {
        this.suggestion = provider;
        return this;
    }

    @Override
    public VelocityCommand requires(Predicate<VelocityCommandSender> requirement) {
        this.requirement = requirement;
        return this;
    }

    @Override
    public VelocityCommand aliases(String... aliases) {
        return null;
    }

    @Override
    public VelocityCommand executes(Executor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public VelocityCommand executesPlayer(VelocityPlayerExecutor executor) {
        this.executorPlayer = executor;
        return this;
    }

    @Override
    public VelocityCommand then(String name) {
        VelocityCommand command = new VelocityCommand(this.platform, name);
        this.children.add(command);
        return command;
    }

    @Override
    public <E extends Argument<A>, A> VelocityCommand then(String name, Class<E> type) {
        VelocityArgument<A> argument = this.platform.getCommandManager().argument(type);
        if (argument == null)
            throw new RuntimeException("Cannot find the argument type");
        VelocityCommand command = new VelocityCommand(this.platform, name, argument, argument.brigadier());
        this.children.add(command);
        return command;
    }

    @Override
    public VelocityCommand thenInteger(String name, int min, int max) {
        ArgumentVelocityInteger argument = (ArgumentVelocityInteger) this.platform.getCommandManager().argument(ArgumentInteger.class);
        VelocityCommand command = new VelocityCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public VelocityCommand thenLong(String name, long min, long max) {
        ArgumentVelocityLong argument = (ArgumentVelocityLong) this.platform.getCommandManager().argument(ArgumentLong.class);
        VelocityCommand command = new VelocityCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public VelocityCommand thenFloat(String name, float min, float max) {
        ArgumentVelocityFloat argument = (ArgumentVelocityFloat) this.platform.getCommandManager().argument(ArgumentFloat.class);
        VelocityCommand command = new VelocityCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public VelocityCommand thenDouble(String name, double min, double max) {
        ArgumentVelocityDouble argument = (ArgumentVelocityDouble) this.platform.getCommandManager().argument(ArgumentDouble.class);
        VelocityCommand command = new VelocityCommand(this.platform, name, argument, argument.brigadier(min, max));
        this.children.add(command);
        return command;
    }

    @Override
    public VelocityCommand clone(String name) {
        VelocityCommand newCommand = new VelocityCommand(this.platform, name, this.argumentType, this.brigadierType);
        newCommand.children.addAll(this.children);
        newCommand.executor = this.executor;
        newCommand.executorPlayer = this.executorPlayer;
        newCommand.suggestion = this.suggestion;
        newCommand.requirement = this.requirement;
        return newCommand;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @SuppressWarnings("unchecked")
    public ArgumentBuilder<CommandSource, ?> toBrigadier() {
        ArgumentBuilder<CommandSource, ?> builder = this.argument ? RequiredArgumentBuilder.argument(this.name, this.brigadierType) : LiteralArgumentBuilder.literal(this.name);
        for (VelocityCommand command : this.children)
            builder.then(command.toBrigadier());
        if (this.requirement != null)
            builder.requires(req -> {
                if (req instanceof Player)
                    return requirement.test(VelocityPlayer.of((Player) req, this.platform));
                else
                    return requirement.test(VelocityConsoleCommandSender.instance(this.platform));
            });
        if (this.suggestion != null && this.argument)
            ((RequiredArgumentBuilder<CommandSource, ?>) builder).suggests(((commandContext, suggestionsBuilder) -> {
                VelocitySuggestion VelocitySuggestion = new VelocitySuggestion();
                suggestion.accept(VelocitySuggestion);
                VelocitySuggestion.getStringSuggestions().forEach(suggestionsBuilder::suggest);
                VelocitySuggestion.getIntSuggestions().forEach(suggestionsBuilder::suggest);
                return suggestionsBuilder.buildFuture();
            }));
        builder.executes(commandContext -> {
            if (commandContext.getSource() instanceof Player && this.executorPlayer != null)
                this.executorPlayer.executes(VelocityPlayer.of((Player) commandContext.getSource(), this.platform), new VelocityCommandContext(this.platform, commandContext));
            else if (this.executor != null)
                this.executor.executes(VelocityConsoleCommandSender.instance(this.platform), new VelocityCommandContext(this.platform, commandContext));
            return 1;
        });
        return builder;
    }

}
