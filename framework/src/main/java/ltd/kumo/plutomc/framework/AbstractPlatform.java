package ltd.kumo.plutomc.framework;

import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.platform.BukkitPlatform;
import ltd.kumo.plutomc.framework.platform.ProxyPlatform;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

/**
 * 抽象框架平台。
 */
@SuppressWarnings("unused")
public abstract class AbstractPlatform<T> {
    @NotNull
    T server;
    @NotNull
    private static final LuckPerms LUCKPERMS_API;

    public AbstractPlatform(T server) {
        this.server = server;
    }

    static {
        LUCKPERMS_API = LuckPermsProvider.get();
    }

    @NotNull
    public static AbstractPlatform<Server> bukkit(@NotNull Server server) {
        return new BukkitPlatform(server);
    }

    @NotNull
    public static AbstractPlatform<ProxyServer> velocity(@NotNull ProxyServer proxyServer) {
        return new ProxyPlatform(proxyServer);
    }

    @NotNull
    public abstract String name();

    @NotNull
    public abstract String version();

    public static LuckPerms getLuckPermsApi() {
        return LUCKPERMS_API;
    }

    @NotNull
    public T getServer() {
        return server;
    }
}
