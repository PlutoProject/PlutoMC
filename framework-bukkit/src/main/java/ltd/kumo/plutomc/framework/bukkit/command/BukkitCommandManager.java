package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.argument.*;
import ltd.kumo.plutomc.framework.bukkit.command.commodore.Commodore;
import ltd.kumo.plutomc.framework.bukkit.command.commodore.CommodoreProvider;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.HashMap;
import java.util.Map;

public class BukkitCommandManager {

    private final BukkitPlatform platform;
    private final CommandDispatcher<BukkitCommandSender> dispatcher = new CommandDispatcher<>();
    private final Commodore commodore;
    private final Map<Class<?>, BukkitArgument<?>> argumentImplementers = new HashMap<>();
    private final CommandMap commandMap;

    public BukkitCommandManager(BukkitPlatform platform) {
        this.platform = platform;
        this.commodore = CommodoreProvider.getCommodore(this.platform.plugin());

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

        ArgumentBukkitPlayer argumentBukkitPlayer = new ArgumentBukkitPlayer();
        this.argumentImplementers.put(ArgumentPlayer.class, argumentBukkitPlayer);
        this.argumentImplementers.put(ArgumentBukkitPlayer.class, argumentBukkitPlayer);

        ArgumentBukkitPlayers argumentBukkitPlayers = new ArgumentBukkitPlayers();
        this.argumentImplementers.put(ArgumentPlayers.class, argumentBukkitPlayers);
        this.argumentImplementers.put(ArgumentBukkitPlayers.class, argumentBukkitPlayers);

        this.commandMap = Bukkit.getServer().getCommandMap();

    }

    public CommandDispatcher<BukkitCommandSender> dispatcher() {
        return dispatcher;
    }

    public void register(String prefix, BukkitCommand command) {
        Command cmd = new SilentCommand(command.name());
        this.commandMap.register(prefix, cmd);
        this.dispatcher.getRoot().addChild(command.toBrigadier().build());
        this.dispatcher.getRoot().addChild(command.clone(prefix + ":" + command.name()).toBrigadier().build());
        System.out.println(command.toBrigadier());
        this.commodore.register(cmd, (LiteralArgumentBuilder<?>) command.toCommodore());
    }

    @SuppressWarnings("unchecked")
    public <E extends Argument<T>, T> BukkitArgument<T> argument(Class<E> clazz) {
        return (BukkitArgument<T>) this.argumentImplementers.get(clazz);
    }

}
