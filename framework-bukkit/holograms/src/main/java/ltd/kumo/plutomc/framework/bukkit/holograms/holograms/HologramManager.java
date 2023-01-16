package ltd.kumo.plutomc.framework.bukkit.holograms.holograms;

import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.actions.ClickType;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.scheduler.S;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.tick.Ticked;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a Manager for handling holograms.
 */
public class HologramManager extends Ticked {

    private final @NonNull Map<String, Hologram> hologramMap;
    private final @NonNull Map<UUID, Long> clickCooldowns;
    private final @NonNull Set<HologramLine> temporaryLines;

    /**
     * Map of holograms to load, when their respective worls loads.
     * <p>
     * There were issues with world management plugins loading worlds
     * after holograms. Due to that, holograms in these worlds were skipped
     * as we can't load holograms, that don't have their world all loaded.
     * <p>
     * Key is the name of the world, and Value is a set of file names
     * of all holograms, that couldn't be loaded due to this world problem.
     *
     * @since 2.7.4
     */
    private final @NonNull Map<String, Set<String>> toLoad;

    public HologramManager() {
        super(20L);
        this.hologramMap = new ConcurrentHashMap<>();
        this.clickCooldowns = new ConcurrentHashMap<>();
        this.temporaryLines = Collections.synchronizedSet(new HashSet<>());
        this.toLoad = new ConcurrentHashMap<>();
        this.register();

        S.async(this::reload); // Reload when worlds are ready
    }

    @Override
    public void tick() {
        for (Hologram hologram : Hologram.getCachedHolograms()) {
            if (hologram.isEnabled()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateVisibility(player, hologram);
                }
            }
        }
    }

    public void updateVisibility(@NonNull Player player) {
        for (Hologram hologram : Hologram.getCachedHolograms()) {
            updateVisibility(player, hologram);
        }
    }

    public void updateVisibility(@NonNull Player player, @NonNull Hologram hologram) {
        if (hologram.isDisabled()) {
            return;
        }

        // Determine the player's display state of this hologram.
        if (hologram.isHideState(player) || (!hologram.isDefaultVisibleState() && !hologram.isShowState(player))) {
            if (hologram.isVisible(player)) {
                hologram.hide(player);
            }
            return;
        }

        if (!hologram.isVisible(player) && hologram.canShow(player) && hologram.isInDisplayRange(player)) {
            hologram.show(player, hologram.getPlayerPage(player));
        } else if (hologram.isVisible(player) && !(hologram.canShow(player) && hologram.isInDisplayRange(player))) {
            hologram.hide(player);
        }
    }

    /**
     * Spawn a temporary line that is going to disappear after the given duration.
     *
     * @param location Location of the line.
     * @param content  Content of the line.
     * @param duration Duration to disappear after. (in ticks)
     * @return The Hologram Line.
     */
    public HologramLine spawnTemporaryHologramLine(@NonNull Location location, String content, long duration) {
        HologramLine line = new HologramLine(null, location, content);
        temporaryLines.add(line);
        line.show();
        S.async(() -> {
            line.destroy();
            temporaryLines.remove(line);
        }, duration);
        return line;
    }

    /**
     * Attempts to process a click on an entity. If the entity is part of a hologram,
     * that is clickable and enabled, the click will be processed.
     *
     * @param player    The player who clicked.
     * @param entityId  Entity ID of the clicked entity.
     * @param clickType Click type.
     * @return True if the click was processed, false otherwise.
     */
    public boolean onClick(@NonNull Player player, int entityId, @NonNull ClickType clickType) {
        UUID uid = player.getUniqueId();

        // Check if the player is on cooldown.
        if (clickCooldowns.containsKey(uid) && System.currentTimeMillis() - clickCooldowns.get(uid) < 5 * 50L) {
            return false;
        }

        for (Hologram hologram : Hologram.getCachedHolograms()) {
            if (hologram.isVisible(player)) {
                if (hologram.onClick(player, entityId, clickType)) {
                    clickCooldowns.put(uid, System.currentTimeMillis());
                    return true;
                }
            }
        }

        return false;
    }

    public void onQuit(@NonNull Player player) {
        Hologram.getCachedHolograms().forEach(hologram -> hologram.onQuit(player));
        clickCooldowns.remove(player.getUniqueId());
    }

    /**
     * Reload this manager and all the holograms.
     */
    public void reload() {
        this.destroy();
    }

    /**
     * Destroy this manager and all the holograms.
     */
    public void destroy() {
        // Destroy registered holograms
        for (Hologram hologram : getHolograms()) {
            hologram.destroy();
        }
        hologramMap.clear();

        // Destroy temporary lines
        for (HologramLine line : temporaryLines) {
            line.destroy();
        }
        temporaryLines.clear();

        clickCooldowns.clear();
    }

    /**
     * Show all registered holograms for the given player.
     *
     * @param player Given player.
     */
    public void showAll(@NonNull Player player) {
        for (Hologram hologram : getHolograms()) {
            if (hologram.isEnabled()) {
                hologram.show(player, hologram.getPlayerPage(player));
            }
        }
        for (HologramLine line : temporaryLines) {
            line.show(player);
        }
    }

    /**
     * Hide all registered holograms for the given player.
     *
     * @param player Given player.
     */
    public void hideAll(@NonNull Player player) {
        for (Hologram hologram : getHolograms()) {
            hologram.hideAll();
        }
        for (HologramLine line : temporaryLines) {
            line.hide();
        }
    }

    /**
     * Check whether a hologram with the given name is registered in this manager.
     *
     * @param name Name of the hologram.
     * @return Boolean whether a hologram with the given name is registered in this manager.
     */
    public boolean containsHologram(@NonNull String name) {
        return hologramMap.containsKey(name);
    }

    /**
     * Register a new hologram.
     *
     * @param hologram New hologram.
     * @return The new hologram or null if it wasn't registered successfully.
     */
    public Hologram registerHologram(@NonNull Hologram hologram) {
        return hologramMap.put(hologram.getName(), hologram);
    }

    /**
     * Get hologram by name.
     *
     * @param name Name of the hologram.
     * @return The hologram or null if it wasn't found.
     */
    public Hologram getHologram(@NonNull String name) {
        return hologramMap.get(name);
    }

    /**
     * Remove hologram by name.
     *
     * @param name Name of the hologram.
     * @return The hologram or null if it wasn't found.
     */
    public Hologram removeHologram(@NonNull String name) {
        return hologramMap.remove(name);
    }

    /**
     * Get the names of all registered holograms.
     *
     * @return Set of the names of all registered holograms.
     */
    public Set<String> getHologramNames() {
        return hologramMap.keySet();
    }

    /**
     * Get all registered holograms.
     *
     * @return Collection of all registered holograms.
     */
    @NonNull
    public Collection<Hologram> getHolograms() {
        return hologramMap.values();
    }

}
