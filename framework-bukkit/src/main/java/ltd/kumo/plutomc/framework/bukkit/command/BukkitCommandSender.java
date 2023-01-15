package ltd.kumo.plutomc.framework.bukkit.command;

import ltd.kumo.plutomc.framework.shared.command.CommandSender;

public abstract class BukkitCommandSender implements CommandSender {

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

    public abstract org.bukkit.command.CommandSender asBukkit();

}
