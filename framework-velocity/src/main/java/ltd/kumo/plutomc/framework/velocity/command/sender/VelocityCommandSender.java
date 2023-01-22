package ltd.kumo.plutomc.framework.velocity.command.sender;

import com.velocitypowered.api.command.CommandSource;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;

public abstract class VelocityCommandSender implements CommandSender {

    private final CommandSource source;

    public VelocityCommandSender(VelocityPlatform platform, CommandSource commandSource) {
        this.source = commandSource;
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

    public CommandSource asVelocity() {
        return this.source;
    }

}
