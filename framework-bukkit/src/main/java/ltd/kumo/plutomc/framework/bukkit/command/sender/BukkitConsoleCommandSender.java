package ltd.kumo.plutomc.framework.bukkit.command.sender;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class BukkitConsoleCommandSender extends BukkitCommandSender {

    public final static BukkitConsoleCommandSender INSTANCE = new BukkitConsoleCommandSender();

    private BukkitConsoleCommandSender() {
        super(Bukkit.getConsoleSender());
    }

    @Override
    public @NotNull String name() {
        return asBukkit().getName();
    }

    @Override
    public boolean isConsole() {
        return true;
    }

}
