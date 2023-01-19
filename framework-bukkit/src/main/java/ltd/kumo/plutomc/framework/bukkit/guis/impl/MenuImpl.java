package ltd.kumo.plutomc.framework.bukkit.guis.impl;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.event.BukkitPlayerQuitEvent;
import ltd.kumo.plutomc.framework.bukkit.guis.Audience;
import ltd.kumo.plutomc.framework.bukkit.guis.Menu;
import ltd.kumo.plutomc.framework.bukkit.guis.Renderer;
import ltd.kumo.plutomc.framework.bukkit.guis.Scene;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MenuImpl implements Menu, Listener {

    private final BukkitPlatform platform;
    private final List<Audience> audiences = new ArrayList<>();

    private boolean dropped = false;

    public MenuImpl(BukkitPlatform platform) {
        this.platform = platform;
        Bukkit.getPluginManager().registerEvents(this, platform.plugin());
    }

    @Override
    public List<? extends Audience> audiences() {
        if (this.dropped)
            return null;
        return new ArrayList<>(audiences);
    }

    @Override
    public Scene scene(Audience audience) {
        if (this.dropped)
            return null;
        if (audiences.contains(audience) && audience instanceof BukkitPlayer player) {
            return player.getScene();
        }
        return null;
    }

    @Override
    public Renderer add(Audience audience) {
        if (this.dropped)
            return null;
        if (!this.audiences.contains(audience))
            this.audiences.add(audience);
        return new RendererImpl(null, this.platform, this, audience);
    }

    @Override
    public void remove(Audience audience) {
        if (this.dropped)
            return;
        if (!this.audiences.contains(audience))
            return;
        this.audiences.remove(audience);
        audience.removeScene();
    }

    @Override
    public void drop() {
        if (this.dropped)
            return;
        this.audiences.forEach(this::remove);
        HandlerList.unregisterAll(this);
        this.dropped = true;
    }

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    public void ensurePlayer(Audience player, SceneImpl scene) {
        if (!this.audiences.contains(player))
            this.audiences.add(player);
        ((BukkitPlayer) player).setScene(scene);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Inventory clickedInventory = event.getClickedInventory();
        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof SceneHolder sceneHolder))
            return;
        if (clickedInventory != topInventory && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            event.setCancelled(true);
            return;
        }
        if (clickedInventory == topInventory) {
            event.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sceneHolder.getScene().processClick(event.getRawSlot(), event.getAction(), event.getClick(), event.getCursor());
                }
            }.runTaskLater(this.platform.plugin(), 1L);
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Inventory topInventory = event.getView().getTopInventory();
        if (!(topInventory.getHolder() instanceof SceneHolder sceneHolder))
            return;
        if (sceneHolder.isSave()) {
            sceneHolder.setSave(false);
            return;
        }
        if (sceneHolder.getMenu() == this)
            this.remove(sceneHolder.getAudience());
        sceneHolder.getScene().getPreviousSettings().getCloser().close(sceneHolder.getScene());
    }

    @EventHandler
    public void quit(BukkitPlayerQuitEvent event) {
        this.audiences.remove(event.getBukkitPlayer());
    }

}
