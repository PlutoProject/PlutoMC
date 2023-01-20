package ltd.kumo.plutomc.modules.whitelist;

import cc.keyimc.keyi.config.ConfigHelper;
import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import ltd.kumo.plutomc.common.whitelistmanager.impl.WhitelistManager;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.velocity.modules.VelocityModule;
import ltd.kumo.plutomc.modules.whitelist.listeners.PlayerListeners;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class WhitelistModule extends VelocityModule {
    @Nullable
    @Getter
    private static WhitelistManager whitelistManager;

    @Nullable
    @Getter
    private static ConfigHelper configHelper;

    @NotNull
    private final File dataDir;

    @Nullable
    @Getter
    private static ProxyServer server;

    @NotNull
    private static final ImmutableList<?> LISTENERS = ImmutableList.of(
            new PlayerListeners()
    );

    @NotNull
    private static final ImmutableList<Command> COMMANDS = ImmutableList.of(
            WhitelistCommand.brigadier()
    );

    @NotNull
    @Getter
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public WhitelistModule(@NotNull Platform<?> platform, @NotNull File dataDir, @NotNull ProxyServer server) {
        super(platform);
        this.dataDir = dataDir;
        WhitelistModule.server = server;
    }

    @Override
    public @NotNull String name() {
        return "Whitelist";
    }

    @Override
    public boolean shouldBeEnabled() {
        return true;
    }

    @Override
    public void initial() {
        try {
            logger().info("Trying to connect database...");

            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            configHelper = new ConfigHelper(new File(dataDir, "config.toml"));
            whitelistManager = new WhitelistManager(
                    configHelper.get("mongodb.host", "127.0.0.1"),
                    configHelper.get("mongodb.port", 27017),
                    configHelper.get("mongodb.user", "minecraft"),
                    configHelper.get("mongodb.password", "GoodPassword123"),
                    configHelper.get("mongodb.database", "whitelist")
            );
        }catch (Exception e) {
            logger().log(Level.SEVERE, "Failed to connect database!", e);
        }


        LISTENERS.forEach(listener -> listener(Objects.requireNonNull(server), listener));
        COMMANDS.forEach(command -> command(Objects.requireNonNull(server), "whitelist", Objects.requireNonNull(command), "wl"));
    }

    @Override
    public void terminate() {
        Objects.requireNonNull(whitelistManager).close();
    }

    @Override
    public void reload() {

    }
}
