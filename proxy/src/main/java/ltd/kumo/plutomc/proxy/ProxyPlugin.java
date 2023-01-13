package ltd.kumo.plutomc.proxy;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.AbstractPlatform;
import ltd.kumo.plutomc.framework.platforms.ProxyPlatform;
import ltd.kumo.plutomc.proxy.listeners.PlayerListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * 代理端插件主类。
 */
@SuppressWarnings("unused")
@Plugin(
        id = "proxy",
        name = "proxy",
        version = "1.0.0",
        dependencies = {
                @Dependency(id = "luckperms")
        }
)
public final class ProxyPlugin {
    @NotNull
    private static final ImmutableList<?> listeners = ImmutableList.of(
            new PlayerListener()
    );
    @Nullable
    private static ProxyPlatform platform;
    @NotNull
    private final ProxyServer server;

    @Inject
    public ProxyPlugin(@NotNull ProxyServer server, @NotNull Logger logger) {
        platform = (ProxyPlatform) AbstractPlatform.velocity(server);
        this.server = server;
    }

    @Nullable
    public static ProxyPlatform getPlatform() {
        return platform;
    }

    @Subscribe
    public void proxyInitializeEvent(ProxyInitializeEvent event) {
        listeners.forEach(o -> server.getEventManager().register(this, o));
    }
}
