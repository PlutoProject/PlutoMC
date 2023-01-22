package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.argument.*;
import ltd.kumo.plutomc.framework.bukkit.command.sender.BukkitCommandSender;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.*;

public class BukkitCommandManager implements Listener {

    private final BukkitPlatform platform;
    private final CommandDispatcher<BukkitCommandSender> dispatcher = new CommandDispatcher<>();
    private final Map<Class<?>, BukkitArgument<?>> argumentImplementers = new HashMap<>();
    private final CommandMap commandMap;

    private final Map<String, Set<CMD>> registered = new HashMap<>();

    public BukkitCommandManager(BukkitPlatform platform) {
        this.platform = platform;

        // Register default command types
        ArgumentBukkitInteger argumentBukkitInteger = new ArgumentBukkitInteger();
        this.argumentImplementers.put(ArgumentInteger.class, argumentBukkitInteger);
        this.argumentImplementers.put(ArgumentBukkitInteger.class, argumentBukkitInteger);

        ArgumentBukkitLong argumentBukkitLong = new ArgumentBukkitLong();
        this.argumentImplementers.put(ArgumentLong.class, argumentBukkitLong);
        this.argumentImplementers.put(ArgumentBukkitLong.class, argumentBukkitLong);

        ArgumentBukkitFloat argumentBukkitFloat = new ArgumentBukkitFloat();
        this.argumentImplementers.put(ArgumentFloat.class, argumentBukkitFloat);
        this.argumentImplementers.put(ArgumentBukkitFloat.class, argumentBukkitFloat);

        ArgumentBukkitDouble argumentBukkitDouble = new ArgumentBukkitDouble();
        this.argumentImplementers.put(ArgumentDouble.class, argumentBukkitDouble);
        this.argumentImplementers.put(ArgumentBukkitDouble.class, argumentBukkitDouble);

        ArgumentBukkitBoolean argumentBukkitBoolean = new ArgumentBukkitBoolean();
        this.argumentImplementers.put(ArgumentBoolean.class, argumentBukkitBoolean);
        this.argumentImplementers.put(ArgumentBukkitBoolean.class, argumentBukkitBoolean);

        ArgumentBukkitString argumentBukkitString = new ArgumentBukkitString();
        this.argumentImplementers.put(ArgumentString.class, argumentBukkitString);
        this.argumentImplementers.put(ArgumentBukkitString.class, argumentBukkitString);

        ArgumentBukkitMessage argumentBukkitMessage = new ArgumentBukkitMessage();
        this.argumentImplementers.put(ArgumentMessage.class, argumentBukkitMessage);
        this.argumentImplementers.put(ArgumentBukkitMessage.class, argumentBukkitMessage);

        this.argumentImplementers.put(ArgumentBukkitPlayer.class, new ArgumentBukkitPlayer());

        this.argumentImplementers.put(ArgumentBukkitPlayers.class, new ArgumentBukkitPlayers());

        this.commandMap = Bukkit.getServer().getCommandMap();
    }

    public CommandDispatcher<BukkitCommandSender> dispatcher() {
        return dispatcher;
    }

    public void register(String prefix, BukkitCommand command) {
        Command vcw = BukkitCommandReflections.CONSTRUCTOR_VANILLA_COMMAND_WRAPPER.newInstance(null, command.toBrigadier().build());
        this.commandMap.register(command.name(), prefix, vcw);
        for (String alias : command.getAliases())
            this.commandMap.register(alias, prefix, vcw);
        if (!registered.containsKey(prefix))
            registered.put(prefix, new HashSet<>());
        this.registered.get(prefix).add(new CMD(prefix, vcw, command));
    }

    @SuppressWarnings("unchecked")
    public <E extends Argument<T>, T> BukkitArgument<T> argument(Class<E> clazz) {
        return (BukkitArgument<T>) this.argumentImplementers.get(clazz);
    }

    @EventHandler
    @SuppressWarnings("unchecked")
    public void serverLoaded(ServerLoadEvent event) {
        Object minecraftServer = BukkitCommandReflections.METHOD_GET_SERVER.invoke(Bukkit.getServer());
        Object bukkitCommandDispatcher = BukkitCommandReflections.METHOD_GET_COMMAND_DISPATCHER.invoke(minecraftServer);
        CommandDispatcher<Object> brigadierCommandDispatcher = (CommandDispatcher<Object>) BukkitCommandReflections.METHOD_GET_BRIGADIER_COMMAND_DISPATCHER.invoke(bukkitCommandDispatcher);
        Map<String, Command> knownCommands = this.commandMap.getKnownCommands();
        for (String prefix : registered.keySet())
            for (CMD commandRecord : registered.get(prefix)) {
                CommandNode<Object> commandNode = commandRecord.bukkitCommand.toBrigadier().build();
                List<String> aliases = new ArrayList<>();
                aliases.add(commandRecord.bukkitCommand.name());
                aliases.add(prefix + ":" + commandRecord.bukkitCommand.name());
                for (String alias : commandRecord.bukkitCommand.getAliases()) {
                    aliases.add(alias);
                    aliases.add(prefix + ":" + alias);
                }
                for (String alias : aliases) {
                    Command command = knownCommands.get(alias);
                    if (command == null)
                        continue;
                    if (!BukkitCommandReflections.CLASS_VANILLA_COMMAND_WRAPPER.original().isInstance(command))
                        continue;
                    if (!Objects.equals(command, commandRecord.command()))
                        continue;
                    brigadierCommandDispatcher.getRoot().addChild(copy(alias, commandNode));
                    BukkitCommandReflections.FIELD_DISPATCHER.set(command, bukkitCommandDispatcher);
                }
            }
    }

    private CommandNode<Object> copy(String name, CommandNode<Object> redirector) {
        return LiteralArgumentBuilder.literal(name)
                .redirect(redirector)
                .build();
    }

    private record CMD(String prefix, Command command, BukkitCommand bukkitCommand) {
    }

}
