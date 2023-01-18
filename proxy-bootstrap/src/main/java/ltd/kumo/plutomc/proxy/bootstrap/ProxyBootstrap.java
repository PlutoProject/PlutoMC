package ltd.kumo.plutomc.proxy.bootstrap;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.framework.velocity.modules.VelocityModule;
import ltd.kumo.plutomc.modules.whitelist.WhitelistModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
@Plugin(
        id = "whitelist",
        name = "Whitelist",
        authors = {"PlutoMC Team", "All contributors"}
)
public class ProxyBootstrap {

    @NotNull
    private ProxyServer server;

    @NotNull
    private Logger logger;

    @NotNull
    private Path dataDir;

    @Nullable
    private static VelocityPlatform platform;

    @Nullable
    private static ImmutableList<VelocityModule> modules;


    @Inject
    public ProxyBootstrap(@NotNull ProxyServer server, @NotNull Logger logger, @NotNull @DataDirectory Path dataDir) {
        this.server = server;
        this.logger = logger;
        this.dataDir = dataDir;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        platform = VelocityPlatform.of((Plugin) this, server);

        modules = ImmutableList.of(
                new WhitelistModule(platform, dataDir.toFile(), server)
        );

        platform.enable();
        platform.enableModules();
    }
}
