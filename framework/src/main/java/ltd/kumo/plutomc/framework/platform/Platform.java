package ltd.kumo.plutomc.framework.platform;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class Platform {
    @NotNull
    public static Platform bukkit(@NotNull Server server) {
        return new BukkitPlatform(server);
    }

    @NotNull
    public abstract String name();

    @NotNull
    public abstract String version();
}
