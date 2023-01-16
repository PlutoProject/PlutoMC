package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitInteger;
import ltd.kumo.plutomc.framework.bukkit.command.commodore.Commodore;
import ltd.kumo.plutomc.framework.bukkit.command.commodore.CommodoreProvider;
import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentInteger;
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
