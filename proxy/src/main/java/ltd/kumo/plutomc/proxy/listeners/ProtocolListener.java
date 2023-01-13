package ltd.kumo.plutomc.proxy.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import ltd.kumo.plutomc.framework.utility.colorpattle.Catppuccin;
import net.kyori.adventure.text.Component;

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
    }
}
