package ltd.kumo.plutomc.proxy.bootstrap;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.modules.whitelist.WhitelistModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@Plugin(
        id = "proxy-bootstrap",
        name = "proxy-bootstrap",
        authors = {"PlutoMC Team", "All contributors"}
)
public class ProxyBootstrap {

    @Nullable
    @Getter
    private static VelocityPlatform platform;
    @Nullable
    @Getter
    private static PluginContainer pluginContainer;
    @NotNull
    @Getter
    private final ProxyServer server;
    @NotNull
    @Getter
    private final Logger logger;
    @NotNull
    @Getter
    private final Path dataDir;


    @Inject
    public ProxyBootstrap(@NotNull ProxyServer server, @NotNull Logger logger, @NotNull @DataDirectory Path dataDir) {
        this.server = server;
        this.logger = logger;
        this.dataDir = dataDir;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        if (server.getPluginManager().getPlugin("proxy-bootstrap").isEmpty()) {
            return;
        }

        pluginContainer = server.getPluginManager().getPlugin("proxy-bootstrap").get();
        platform = VelocityPlatform.of(pluginContainer, server);

        platform.modules(ImmutableList.of(
                new WhitelistModule(platform, dataDir.toFile(), server)
        ));

        platform.enable();
        platform.enableModules();
    }
}
