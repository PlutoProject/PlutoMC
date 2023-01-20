package ltd.kumo.plutomc.modules.whitelist.listeners;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import ltd.kumo.plutomc.modules.whitelist.WhitelistModule;
import net.kyori.adventure.text.Component;

import java.util.Objects;

@SuppressWarnings("unused")
public final class PlayerListeners {
    @Subscribe
    public void loginEvent(LoginEvent loginEvent) {
        Player player = loginEvent.getPlayer();

        if (!Objects.requireNonNull(WhitelistModule.getWhitelistManager()).hasWhitelist(player.getUniqueId())) {
            loginEvent.setResult(ResultedEvent.ComponentResult.denied(Component.text("您需要先获得白名单才可以进行游戏。").color(Catppuccin.MOCHA.RED)));
        }
    }
}
