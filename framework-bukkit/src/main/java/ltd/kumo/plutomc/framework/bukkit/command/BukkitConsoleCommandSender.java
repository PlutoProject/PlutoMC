package ltd.kumo.plutomc.framework.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class BukkitConsoleCommandSender extends BukkitCommandSender {

    public final static BukkitConsoleCommandSender INSTANCE = new BukkitConsoleCommandSender();

    private BukkitConsoleCommandSender() {
    }

    @Override
    public CommandSender asBukkit() {
        return Bukkit.getConsoleSender();
    }

    @Override
    public @NotNull String name() {
        return asBukkit().getName();
    }

    @Override
    public void send(String message) {
        asBukkit().sendMessage(message);
    }

    @Override
    public boolean isConsole() {
        return true;
    }

}
