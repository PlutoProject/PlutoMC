package club.plutomc.plutoproject.framework.velocity.command.sender;

import club.plutomc.plutoproject.framework.velocity.VelocityPlatform;
import com.velocitypowered.api.command.CommandSource;
import club.plutomc.plutoproject.framework.shared.command.CommandSender;

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
