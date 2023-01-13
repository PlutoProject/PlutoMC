package ltd.kumo.plutomc.proxy;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.AbstractPlatform;
import ltd.kumo.plutomc.framework.platforms.ProxyPlatform;
import ltd.kumo.plutomc.proxy.listeners.PlayerListener;
import ltd.kumo.plutomc.proxy.listeners.ProtocolListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

/**
 * 代理端插件主类。
 */
@SuppressWarnings("unused")
@Plugin(
        id = "proxy",
        name = "proxy",
        version = "1.0.0",
        description = "Proxy plugin",
        authors = {"PlutoMC Team and all contributors"},
        dependencies = {
                @Dependency(id = "luckperms")
        }
)
public final class ProxyPlugin {
    @NotNull
    private static final ImmutableList<?> listeners = ImmutableList.of(
            new PlayerListener(),
            new ProtocolListener()
    );

    @Nullable
    private static ProxyPlatform platform;

    @Nullable
    private static ProxyServer server;

    @Inject
    public ProxyPlugin(@NotNull ProxyServer server, @NotNull Logger logger) {
        ProxyPlugin.server = server;
    }

    @Nullable
    public static ProxyPlatform getPlatform() {
        return platform;
    }

    public static ProxyServer getServer() {
        return server;
    }

    @Subscribe
    public void proxyInitializeEvent(ProxyInitializeEvent event) {
        platform = (ProxyPlatform) AbstractPlatform.velocity(server);
        listeners.forEach(o -> server.getEventManager().register(this, o));
    }
}
