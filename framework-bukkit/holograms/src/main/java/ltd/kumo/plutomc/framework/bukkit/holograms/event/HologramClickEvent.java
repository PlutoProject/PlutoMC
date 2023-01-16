package ltd.kumo.plutomc.framework.bukkit.holograms.event;

import lombok.Getter;
import ltd.kumo.plutomc.framework.bukkit.holograms.actions.ClickType;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.Hologram;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.HologramPage;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a player clicks on a Hologram.
 */
@Getter
public class HologramClickEvent extends Event implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;
    private final @NotNull Player player;
    private final @NotNull Hologram hologram;
    private final @NotNull HologramPage page;
    private final @NotNull ClickType click;
    private final int entityId;

    public HologramClickEvent(@NotNull Player player, @NotNull Hologram hologram, @NotNull HologramPage page, @NotNull ClickType click, int entityId) {
        super(true);
        this.player = player;
        this.hologram = hologram;
        this.page = page;
        this.click = click;
        this.entityId = entityId;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
