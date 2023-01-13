package ltd.kumo.plutomc.framework.platform;

import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.AbstractPlatform;
import org.jetbrains.annotations.NotNull;

public class ProxyPlatform extends AbstractPlatform<ProxyServer> {
    public ProxyPlatform(ProxyServer server) {
        super(server);
    }

    @Override
    public @NotNull String name() {
        return "Velocity";
    }

    @Override
    public @NotNull String version() {
        return getServer().getVersion().getVersion();
    }
}
