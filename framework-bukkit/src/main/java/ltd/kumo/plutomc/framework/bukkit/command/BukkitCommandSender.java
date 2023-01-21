package ltd.kumo.plutomc.framework.bukkit.command;

import ltd.kumo.plutomc.framework.shared.command.CommandSender;

public abstract class BukkitCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public BukkitCommandSender(org.bukkit.command.CommandSender sender) {
        this.sender = sender;
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
