package ltd.kumo.plutomc.proxy;

import com.google.common.eventbus.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.AbstractPlatform;
import ltd.kumo.plutomc.framework.platforms.ProxyPlatform;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.logging.Logger;

@Plugin(
        id = "proxy",
        name = "proxy",
        version = "1.0.0",
        dependencies = {
                @Dependency(id = "luckperms")
        }
)
public final class ProxyPlugin {
    @Nullable
    private static ProxyPlatform platform;

    @Inject
    public ProxyPlugin(ProxyServer server, Logger logger) {
        platform = (ProxyPlatform) AbstractPlatform.velocity(server);
    }

    @Subscribe
    public void proxyInitializeEvent(ProxyInitializeEvent event) {

    }

    @Nullable
    public static ProxyPlatform getPlatform() {
        return platform;
    }
}
