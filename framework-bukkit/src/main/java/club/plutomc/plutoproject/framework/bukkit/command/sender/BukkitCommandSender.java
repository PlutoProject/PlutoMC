package club.plutomc.plutoproject.framework.bukkit.command.sender;

import club.plutomc.plutoproject.framework.shared.command.CommandSender;
import net.kyori.adventure.text.Component;

public abstract class BukkitCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public BukkitCommandSender(org.bukkit.command.CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String message) {
        this.asBukkit().sendMessage(message);
    }

    @Override
    public void send(Component message) {
        this.asBukkit().sendMessage(message);
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean isBlock() {
        return false;
    }

    public org.bukkit.command.CommandSender asBukkit() {
        return this.sender;
    }

}
