package ltd.kumo.plutomc.proxy.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import ltd.kumo.plutomc.framework.player.ProxyPlayer;

/**
 * 玩家监听器。
 */
@SuppressWarnings("unused")
public final class PlayerListener {
    @Subscribe
    public void postLoginEvent(PostLoginEvent event) {
        Player player = event.getPlayer();
        var proxyPlayer = ProxyPlayer.of(player);

        var metaContainer = proxyPlayer.metaContainer();

        metaContainer.getWithDefault("time.first_join_time", String.valueOf(System.currentTimeMillis()));
        metaContainer.set("time.last_join_time", String.valueOf(System.currentTimeMillis()));

        metaContainer.apply();
    }

    @Subscribe
    public void disconnectEvent(DisconnectEvent event) {
        Player player = event.getPlayer();
        var proxyPlayer = ProxyPlayer.of(player);

        var metaContainer = proxyPlayer.metaContainer();

        metaContainer.getWithDefault("time.first_quit_time", String.valueOf(System.currentTimeMillis()));
        metaContainer.set("time.last_quit_time", String.valueOf(System.currentTimeMillis()));

        metaContainer.apply();
    }
}
