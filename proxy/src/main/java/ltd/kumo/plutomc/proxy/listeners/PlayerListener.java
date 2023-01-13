package ltd.kumo.plutomc.proxy.listeners;

import com.google.common.eventbus.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import ltd.kumo.plutomc.framework.player.AbstractPlayer;

public final class PlayerListener {
    @Subscribe
    public void postLoginEvent(PostLoginEvent event) {
        var player = event.getPlayer();
        var proxyPlayer = AbstractPlayer.velocity(player);

        var metaContainer = proxyPlayer.metaContainer();

        metaContainer.getWithDefault("time.first_join_time", String.valueOf(System.currentTimeMillis()));
        metaContainer.set("time.last_join_time", String.valueOf(System.currentTimeMillis()));

        metaContainer.apply();
    }

    @Subscribe
    public void disconnectEvent(DisconnectEvent event) {
        var player = event.getPlayer();
        var proxyPlayer = AbstractPlayer.velocity(player);

        var metaContainer = proxyPlayer.metaContainer();

        metaContainer.getWithDefault("time.first_quit_time", String.valueOf(System.currentTimeMillis()));
        metaContainer.set("time.last_quit_time", String.valueOf(System.currentTimeMillis()));

        metaContainer.apply();
    }
}
