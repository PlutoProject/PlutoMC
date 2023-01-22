package ltd.kumo.plutomc.framework.bukkit.player;

import ltd.kumo.plutomc.framework.bukkit.command.sender.BukkitCommandSender;
import ltd.kumo.plutomc.framework.bukkit.event.BukkitPlayerQuitEvent;
import ltd.kumo.plutomc.framework.bukkit.gui.Audience;
import ltd.kumo.plutomc.framework.bukkit.gui.Scene;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public final class BukkitPlayer extends BukkitCommandSender implements Player<org.bukkit.entity.Player>, Audience {

    private final static Map<UUID, BukkitPlayer> CACHE = new HashMap<>();
    public final static Listener LISTENER = new Listener() {
        @EventHandler
        public void quit(PlayerQuitEvent event) {
            BukkitPlayer bukkitPlayer = of(event.getPlayer());
            CACHE.remove(bukkitPlayer.uuid());
            Bukkit.getPluginManager().callEvent(new BukkitPlayerQuitEvent(bukkitPlayer));
        }
    };

    private final UUID player;
    private Scene scene;

    private BukkitPlayer(@NotNull UUID player) {
        super(Bukkit.getPlayer(player));
        this.player = player;
    }

    public static @NotNull BukkitPlayer of(org.bukkit.entity.Player player) {
        return of(player.getUniqueId());
    }

    public static @NotNull BukkitPlayer of(UUID uuid) {
        if (CACHE.containsKey(uuid))
            return CACHE.get(uuid);
        BukkitPlayer bukkitPlayer = new BukkitPlayer(uuid);
        CACHE.put(uuid, bukkitPlayer);
        return bukkitPlayer;
    }

    @Override
    public @Nullable org.bukkit.entity.Player player() {
        return Bukkit.getPlayer(uuid());
    }

    @Override
    public @Nullable Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public @NotNull UUID uuid() {
        return this.player;
    }

    @Override
    public void removeScene() {
        Objects.requireNonNull(this.player()).closeInventory();
        this.scene = null;
    }

    @Override
    public @NotNull String name() {
        org.bukkit.entity.Player player = this.player();
        return player != null ? player.getName() : "null";
    }

    @Override
    public void send(String message) {
        org.bukkit.entity.Player player = this.player();
        if (player == null)
            return;
        player.sendMessage(message);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public CommandSender asBukkit() {
        return this.player();
    }

}
