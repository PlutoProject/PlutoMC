package club.plutomc.plutoproject.framework.bukkit.event;

import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BukkitPlayerQuitEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();

    private final BukkitPlayer bukkitPlayer;

    public BukkitPlayerQuitEvent(BukkitPlayer bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public BukkitPlayer getBukkitPlayer() {
        return bukkitPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
