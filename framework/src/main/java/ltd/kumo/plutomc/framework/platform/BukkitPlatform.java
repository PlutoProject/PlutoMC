package ltd.kumo.plutomc.framework.platform;

import ltd.kumo.plutomc.framework.AbstractPlatform;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit 框架平台。
 */
@SuppressWarnings("unused")
public final class BukkitPlatform extends AbstractPlatform {
    private final Server BUKKIT_SERVER;

    public BukkitPlatform(Server server) {
        this.BUKKIT_SERVER = server;
    }

    @Override
    @NotNull
    public String name() {
        return "Bukkit";
    }

    @Override
    @NotNull
    public String version() {
        return BUKKIT_SERVER.getBukkitVersion();
    }
}
