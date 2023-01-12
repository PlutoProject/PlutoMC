package ltd.kumo.plutomc.framework.platform;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public abstract class Platform {
    @Nullable
    public static Platform bukkit(Server server) {
        if (server == null) {
            return null;
        }

        return new BukkitPlatform(server);
    }

    @NotNull
    public abstract String name();

    @NotNull
    public abstract String version();
}
