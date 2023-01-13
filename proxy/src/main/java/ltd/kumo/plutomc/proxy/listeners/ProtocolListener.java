package ltd.kumo.plutomc.proxy.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import com.velocitypowered.api.util.ModInfo;
import ltd.kumo.plutomc.framework.utility.colorpattle.Catppuccin;
import ltd.kumo.plutomc.proxy.ProxyPlugin;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 协议监听器。
 */
@SuppressWarnings("unused")
public class ProtocolListener {
    @Subscribe
    public void proxyPingEvent(ProxyPingEvent event) {
        Component desc = Component.text("PlutoMC")
                .color(Catppuccin.MOCHA.BLUE)
                .append(Component.text(" | ").color(Catppuccin.MOCHA.SURFACE_0))
                .append(Component.text(" 二周目已开放！").color(Catppuccin.MOCHA.GREEN))
                .appendNewline()
                .append(Component.text("服务器目前正在不断开发中，敬请期待。").color(Catppuccin.MOCHA.PEACH));

        final @NotNull ServerPing serverPing;

        serverPing = ServerPing.builder()
                .onlinePlayers(ProxyPlugin.getServer().getPlayerCount())
                .modType("FML")
                .mods(new ModInfo.Mod("PlutoMC", "2.0.0"))
                .maximumPlayers(0)
                .version(new ServerPing.Version(761, "1.19.3"))
                .description(desc).build();

        File file = new File("icon.png");

        if (file.exists()) {
            final @NotNull URL url;
            final @NotNull BufferedImage bufferedImage;

            try {
                url = new URL("file://" + file.getAbsolutePath());
                bufferedImage = ImageIO.read(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            serverPing.asBuilder().favicon(Favicon.create(bufferedImage));
        }

        event.setPing(serverPing);
    }
}
