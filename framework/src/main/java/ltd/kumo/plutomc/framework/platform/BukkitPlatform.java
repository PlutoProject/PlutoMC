package ltd.kumo.plutomc.framework.platform;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class BukkitPlatform extends Platform {
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
