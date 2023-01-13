package ltd.kumo.plutomc.framework.platforms;

import ltd.kumo.plutomc.framework.AbstractPlatform;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit 框架平台。
 */
@SuppressWarnings("unused")
public final class BukkitPlatform extends AbstractPlatform<Server> {

    public BukkitPlatform(Server server) {
        super(server);
    }

    @Override
    @NotNull
    public String name() {
        return "Bukkit";
    }

    @Override
    @NotNull
    public String version() {
        return getServer().getBukkitVersion();
    }
}
