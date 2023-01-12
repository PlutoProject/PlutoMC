package ltd.kumo.plutomc.framework.platform;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class Platform {
    @NotNull
    private static final LuckPerms LUCKPERMS_API;

    static {
        LUCKPERMS_API = LuckPermsProvider.get();
    }

    @NotNull
    public static Platform bukkit(@NotNull Server server) {
        return new BukkitPlatform(server);
    }

    @NotNull
    public abstract String name();

    @NotNull
    public abstract String version();

    public static LuckPerms getLuckPermsApi() {
        return LUCKPERMS_API;
    }
}
