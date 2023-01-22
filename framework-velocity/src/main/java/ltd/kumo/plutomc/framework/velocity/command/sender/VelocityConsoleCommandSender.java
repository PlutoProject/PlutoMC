package ltd.kumo.plutomc.framework.velocity.command.sender;

import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class VelocityConsoleCommandSender extends VelocityCommandSender {

    public static VelocityConsoleCommandSender instance(VelocityPlatform platform) {
        return new VelocityConsoleCommandSender(platform);
    }

    private VelocityConsoleCommandSender(VelocityPlatform platform) {
        super(platform, platform.getProxyServer().getConsoleCommandSource());
    }

    @Override
    public @NotNull String name() {
        return "Console";
    }

    @Override
    public void send(String message) {
        asVelocity().sendMessage(Component.text(message));
    }

}
