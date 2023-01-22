package ltd.kumo.plutomc.framework.bukkit.holograms.holograms;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHolograms;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.holograms.actions.ClickType;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.enums.EnumFlag;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.objects.UpdatingHologramObject;
import ltd.kumo.plutomc.framework.bukkit.holograms.nms.NMS;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.collection.PList;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.event.EventFactory;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.reflect.Version;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.scheduler.S;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.tick.ITicked;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Hologram extends UpdatingHologramObject implements ITicked {

    private static final PlutoHolograms PLUTO_HOLOGRAMS = PlutoHologramsAPI.get();

    /*
     *	Hologram Cache
     */

    private static final @NonNull Map<String, Hologram> CACHED_HOLOGRAMS;

    static {
        CACHED_HOLOGRAMS = new ConcurrentHashMap<>();
    }

    protected final @NonNull String name;
    protected final @NonNull Map<UUID, Integer> viewerPages = new ConcurrentHashMap<>();
    protected final @NonNull Set<UUID> hidePlayers = Collections.synchronizedSet(new HashSet<>());

    /*
     *	Fields
     */
    protected final @NonNull Set<UUID> showPlayers = Collections.synchronizedSet(new HashSet<>());
    protected final @NonNull PList<HologramPage> pages = new PList<>();
    private final @NonNull AtomicInteger tickCounter;
    protected boolean defaultVisibleState = true;
    protected boolean downOrigin = false;
    protected boolean alwaysFacePlayer = false;

    public Hologram(@NonNull String name, @NonNull Location location) {
        this(name, location, true);
    }

    public Hologram(@NonNull String name, @NonNull Location location, boolean enabled) {
        super(location);
        this.name = name;
        this.enabled = enabled;
        this.tickCounter = new AtomicInteger();
        this.addPage();
        this.register();
    }

    public static Hologram getCachedHologram(@NonNull String name) {
        return CACHED_HOLOGRAMS.get(name);
    }

    /*
     *	Constructors
     */

    @NonNull
    @Contract(pure = true)
    public static Set<String> getCachedHologramNames() {
        return CACHED_HOLOGRAMS.keySet();
    }

    @NonNull
    @Contract(pure = true)
    public static Collection<Hologram> getCachedHolograms() {
        return CACHED_HOLOGRAMS.values();
    }

    /*
     *	Tick
     */

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public long getInterval() {
        return 1L;
    }

    @Override
    public void tick() {
        if (tickCounter.get() == getUpdateInterval()) {
            tickCounter.set(1);
            updateAll();
            return;
        }
        tickCounter.incrementAndGet();
        updateAnimationsAll();
    }

    /*
     *	General Methods
     */

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "name=" + getName() +
                ", enabled=" + isEnabled() +
                "} " + super.toString();
    }

    @Override
    public void destroy() {
        this.disable(DisableCause.API);
        this.viewerPages.clear();
        PLUTO_HOLOGRAMS.getHologramManager().removeHologram(getName());
        CACHED_HOLOGRAMS.remove(getName());
    }

    @Override
    public void enable() {
        super.enable();
        this.showAll();
        this.register();
    }

    @Override
    public void disable(@NonNull DisableCause cause) {
        this.unregister();
        this.hideAll();
        super.disable(cause);
    }

    @Override
    public void setFacing(float facing) {
        float prev = this.facing;

        super.setFacing(facing);

        // Update the facing for all lines, that don't already have a different facing set.
        // We want to keep the hologram facing working as a "default" value, but we don't want
        // it to override custom line facing.
        for (HologramPage page : this.pages) {
            page.getLines().forEach((line) -> {
                if (line.getFacing() == prev) {
                    line.setFacing(facing);
                }
                page.realignLines();
            });
        }
    }

    @Override
    public void setLocation(@NonNull Location location) {
        super.setLocation(location);
        this.hideClickableEntitiesAll();
        this.showClickableEntitiesAll();
    }

    /**
     * Get hologram size. (Number of pages)
     *
     * @return Number of pages in this hologram.
     */
    public int size() {
        return pages.size();
    }

    /**
     * Create a new instance of this hologram object that's identical to this one.
     *
     * @param name     Name of the clone.
     * @param location Location of the clone.
     * @param temp     True if the clone should only exist until the next reload. (Won't save to file)
     * @return Cloned instance of this line.
     */
    public Hologram clone(@NonNull String name, @NonNull Location location, boolean temp) {
        Hologram hologram = new Hologram(name, location.clone(), !temp);
        hologram.setDownOrigin(this.isDownOrigin());
        hologram.setPermission(this.getPermission());
        hologram.setFacing(this.getFacing());
        hologram.setDisplayRange(this.getDisplayRange());
        hologram.setUpdateRange(this.getUpdateRange());
        hologram.setUpdateInterval(this.getUpdateInterval());
        hologram.addFlags(this.getFlags().toArray(new EnumFlag[0]));
        hologram.setDefaultVisibleState(this.isDefaultVisibleState());
        hologram.showPlayers.addAll(this.showPlayers);
        hologram.hidePlayers.addAll(this.hidePlayers);

        for (int i = 0; i < size(); i++) {
            HologramPage page = getPage(i);
            HologramPage clonePage = page.clone(hologram, i);
            if (hologram.pages.size() > i) {
                hologram.pages.set(i, clonePage);
            } else {
                hologram.pages.add(clonePage);
            }
        }
        return hologram;
    }

    public boolean onClick(@NonNull Player player, int entityId, @NonNull ClickType clickType) {
        if (this.hasFlag(EnumFlag.DISABLE_ACTIONS)) {
            return false;
        }
        HologramPage page = getPage(player);
        if (page != null && page.hasEntity(entityId)) {
            EventFactory.handleHologramInteractEvent(player, this, page, clickType, entityId);
            return true;
        }
        return false;
    }

    public void onQuit(@NonNull Player player) {
        hide(player);
        removeShowPlayer(player);
        removeHidePlayer(player);
        viewerPages.remove(player.getUniqueId());
    }

    /*
     *	Visibility Methods
     */

    /**
     * Set default display state
     *
     * @param state state
     */
    public void setDefaultVisibleState(boolean state) {
        this.defaultVisibleState = state;
    }

    /**
     * @return Default display state
     */
    public boolean isVisibleState() {
        return defaultVisibleState;
    }

    /**
     * Set player hide state
     *
     * @param player player
     */
    public void setHidePlayer(@NonNull Player player) {
        UUID uniqueId = player.getUniqueId();
        if (!hidePlayers.contains(uniqueId)) {
            hidePlayers.add(player.getUniqueId());
        }
    }

    /**
     * Remove a player hide state
     *
     * @param player player
     */
    public void removeHidePlayer(@NonNull Player player) {
        UUID uniqueId = player.getUniqueId();
        hidePlayers.remove(uniqueId);
    }

    /**
     * Determine if the player can't see the hologram
     *
     * @param player player
     * @return state
     */
    public boolean isHideState(@NonNull Player player) {
        return hidePlayers.contains(player.getUniqueId());
    }

    /**
     * Set player show state
     *
     * @param player player
     */
    public void setShowPlayer(@NonNull Player player) {
        UUID uniqueId = player.getUniqueId();
        if (!showPlayers.contains(uniqueId)) {
            showPlayers.add(player.getUniqueId());
        }
    }

    /**
     * Remove a player show state
     *
     * @param player player
     */
    public void removeShowPlayer(@NonNull Player player) {
        UUID uniqueId = player.getUniqueId();
        showPlayers.remove(uniqueId);
    }

    /**
     * Determine if the player can see the hologram
     *
     * @param player player
     * @return state
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isShowState(@NonNull Player player) {
        return showPlayers.contains(player.getUniqueId());
    }

    /**
     * Show this hologram for given player on a given page.
     *
     * @param player    Given player.
     * @param pageIndex Given page.
     */
    public boolean show(@NonNull Player player, int pageIndex) {
        if (isDisabled() || isHideState(player) || (!isDefaultVisibleState() && !isShowState(player))) {
            return false;
        }
        HologramPage page = getPage(pageIndex);
        if (page != null && page.size() > 0 && canShow(player) && isInDisplayRange(player)) {
            if (isVisible(player)) {
                hide(player);
            }
            if (Version.after(8)) {
                showPageTo(player, page, pageIndex);
            } else {
                // We need to run the task later on older versions as, if we don't, it causes issues with some holograms *randomly* becoming invisible.
                // I *think* this is from despawning and spawning the entities (with the same ID) in the same tick.
                S.sync(() -> showPageTo(player, page, pageIndex), 0L);
            }
            return true;
        }
        return false;
    }

    public boolean show(@NotNull Player player) {
        return show(player, getPlayerPage(player));
    }

    private void showPageTo(@NonNull Player player, @NonNull HologramPage page, int pageIndex) {
        page.getLines().forEach(line -> line.show(player));
        // Add player to viewers
        viewerPages.put(player.getUniqueId(), pageIndex);
        viewers.add(player.getUniqueId());
        showClickableEntities(player);
    }

    public void showAll() {
        if (isEnabled()) {
            Bukkit.getOnlinePlayers().forEach(player -> show(player, getPlayerPage(player)));
        }
    }

    public void update(@NonNull Player player) {
        if (hasFlag(EnumFlag.DISABLE_UPDATING) || !isVisible(player) || !isInUpdateRange(player) || isHideState(player)) {
            return;
        }

        HologramPage page = getPage(player);
        if (page != null) {
            page.getLines().forEach(line -> line.update(player));
        }
    }

    public void updateAll() {
        if (isEnabled() && !hasFlag(EnumFlag.DISABLE_UPDATING)) {
            getViewerPlayers().forEach(this::update);
        }
    }

    public void updateAnimations(@NonNull Player player) {
        if (hasFlag(EnumFlag.DISABLE_ANIMATIONS) || !isVisible(player) || !isInUpdateRange(player) || isHideState(player)) {
            return;
        }

        HologramPage page = getPage(player);
        if (page != null) {
            page.getLines().forEach(line -> line.updateAnimations(player));
        }
    }

    public void updateAnimationsAll() {
        if (isEnabled() && !hasFlag(EnumFlag.DISABLE_ANIMATIONS)) {
            getViewerPlayers().forEach(this::updateAnimations);
        }
    }

    public void hide(@NonNull Player player) {
        if (isVisible(player)) {
            HologramPage page = getPage(player);
            if (page != null) {
                page.getLines().forEach(line -> line.hide(player));
                hideClickableEntities(player);
            }
            viewers.remove(player.getUniqueId());
        }
    }

    public void hideAll() {
        if (isEnabled()) {
            getViewerPlayers().forEach(this::hide);
        }
    }

    public void showClickableEntities(@NonNull Player player) {
        HologramPage page = getPage(player);
        if (page == null) {
            return;
        }

        // Spawn clickable entities
        NMS nms = NMS.getInstance();
        int amount = (int) (page.getHeight() / 2) + 1;
        Location location = getLocation().clone();
        location.setY((int) (location.getY() - (isDownOrigin() ? 0 : page.getHeight())) + 0.5);
        for (int i = 0; i < amount; i++) {
            int id = page.getClickableEntityId(i);
            nms.showFakeEntityArmorStand(player, location, id, true, false, true);
            location.add(0, 1.8, 0);
        }
    }

    public void showClickableEntitiesAll() {
        if (isEnabled()) {
            getViewerPlayers().forEach(this::showClickableEntities);
        }
    }

    public void hideClickableEntities(@NonNull Player player) {
        HologramPage page = getPage(player);
        if (page == null) {
            return;
        }

        // Despawn clickable entities
        NMS nms = NMS.getInstance();
        page.getClickableEntityIds().forEach(id -> nms.hideFakeEntities(player, id));
    }

    public void hideClickableEntitiesAll() {
        if (isEnabled()) {
            getViewerPlayers().forEach(this::hideClickableEntities);
        }
    }

    /**
     * Check whether the given player is in display range of this hologram object.
     *
     * @param player Given player.
     * @return Boolean whether the given player is in display range of this hologram object.
     */
    public boolean isInDisplayRange(@NonNull Player player) {
        /*
         * Some forks (e.g. Pufferfish) throw an exception, when we try to get
         * the world of a location, which is not loaded. We catch this exception
         * and return false, because the player is not in range.
         */
        try {
            if (player != null && player.getWorld().equals(location.getWorld())) {
                return player.getLocation().distanceSquared(location) <= displayRange * displayRange;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Check whether the given player is in update range of this hologram object.
     *
     * @param player Given player.
     * @return Boolean whether the given player is in update range of this hologram object.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInUpdateRange(@NonNull Player player) {
        /*
         * Some forks (e.g. Pufferfish) throw an exception, when we try to get
         * the world of a location, which is not loaded. We catch this exception
         * and return false, because the player is not in range.
         */
        try {
            if (player != null && player.getWorld().equals(location.getWorld())) {
                return player.getLocation().distanceSquared(location) <= updateRange * updateRange;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public void setDownOrigin(boolean downOrigin) {
        this.downOrigin = downOrigin;
        this.hideClickableEntitiesAll();
        this.showClickableEntitiesAll();
    }

    /*
     *	Viewer Methods
     */

    public int getPlayerPage(@NonNull Player player) {
        return viewerPages.getOrDefault(player.getUniqueId(), 0);
    }

    public Set<Player> getViewerPlayers(int pageIndex) {
        Set<Player> players = new HashSet<>();
        viewerPages.forEach((uuid, integer) -> {
            if (integer == pageIndex) {
                players.add(Bukkit.getPlayer(uuid));
            }
        });
        return players;
    }

    /*
     *	Pages Methods
     */

    /**
     * Re-Align the lines in this hologram putting them to the right place.
     * <p>
     * This method is good to use after teleporting the hologram.
     * </p>
     */
    public void realignLines() {
        for (HologramPage page : pages) {
            page.realignLines();
        }
    }

    public HologramPage addPage() {
        HologramPage page = new HologramPage(this, pages.size());
        pages.add(page);
        return page;
    }

    public HologramPage insertPage(int index) {
        if (index < 0 || index > size()) return null;
        HologramPage page = new HologramPage(this, index);
        pages.add(index, page);

        // Add 1 to indexes of all the other pages.
        pages.stream().skip(index).forEach(p -> p.setIndex(p.getIndex() + 1));
        // Add 1 to all page indexes of current viewers, so they still see the same page.
        viewerPages.replaceAll((uuid, integer) -> {
            if (integer > index) {
                return integer + 1;
            }
            return integer;
        });
        return page;
    }

    public HologramPage getPage(int index) {
        if (index < 0 || index >= size()) return null;
        return pages.get(index);
    }

    public HologramPage getPage(@NonNull Player player) {
        if (isVisible(player)) {
            return getPage(getPlayerPage(player));
        }
        return null;
    }

    public HologramPage removePage(int index) {
        if (index < 0 || index > size()) return null;
        HologramPage page = pages.remove(index);
        page.getLines().forEach(HologramLine::hide);

        // Update indexes of all the other pages.
        for (int i = 0; i < pages.size(); i++) {
            pages.get(i).setIndex(i);
        }
        // Update all page indexes of current viewers, so they still see the same page.
        if (pages.size() > 0) {
            for (UUID uuid : viewerPages.keySet()) {
                int currentPage = viewerPages.get(uuid);
                if (currentPage == index) {
                    show(Bukkit.getPlayer(uuid), 0);
                } else if (currentPage > index) {
                    viewerPages.put(uuid, currentPage - 1);
                }
            }
        }
        return page;
    }

    public boolean swapPages(int index1, int index2) {
        if (index1 == index2 || index1 < 0 || index1 >= size() || index2 < 0 || index2 >= size()) {
            return false;
        }
        // Swap them in the list
        Collections.swap(pages, index1, index2);

        // Swap indexes of affected pages
        HologramPage page1 = getPage(index1);
        HologramPage page2 = getPage(index2);
        int i = page1.getIndex();
        page1.setIndex(page2.getIndex());
        page2.setIndex(i);

        // Swap viewers
        Set<Player> viewers1 = getViewerPlayers(index1);
        Set<Player> viewers2 = getViewerPlayers(index2);
        viewers1.forEach(player -> show(player, index2));
        viewers2.forEach(player -> show(player, index1));
        return true;
    }

    /**
     * Get the list of all pages in this hologram.
     *
     * @return List of all pages in this hologram.
     */
    public List<HologramPage> getPages() {
        return ImmutableList.copyOf(pages);
    }

}
