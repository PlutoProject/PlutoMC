package ltd.kumo.plutomc.framework.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;

public abstract class VelocityCommandSender implements CommandSender {

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

    public abstract CommandSource asVelocity();

}
