package ltd.kumo.plutomc.framework.bukkit.holograms.holograms;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHolograms;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.enums.EnumFlag;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.enums.HologramLineType;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.objects.HologramObject;
import ltd.kumo.plutomc.framework.bukkit.holograms.nms.NMS;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.Common;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.PAPI;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.entity.HologramEntity;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.entity.PlutoEntityType;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.items.HologramItem;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.reflect.Version;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@Setter
public class HologramLine extends HologramObject {

    protected static final PlutoHolograms PLUTO_HOLOGRAMS = PlutoHologramsAPI.get();

    /*
     *	Fields
     */

    private final @Nullable HologramPage parent;
    private final @NonNull Map<UUID, String> playerTextMap = new ConcurrentHashMap<>();
    private final @NonNull Map<UUID, String> lastTextMap = new ConcurrentHashMap<>();
    private HologramLineType type;
    private int[] entityIds = new int[2];
    private final @NonNull AtomicDouble offsetX = new AtomicDouble(0d);
    private final @NonNull AtomicDouble offsetY = new AtomicDouble(0d);
    private final @NonNull AtomicDouble offsetZ = new AtomicDouble(0d);
    private double height;
    private @NonNull String content;
    private String text;
    private HologramItem item;
    private HologramEntity entity;

    private volatile boolean containsAnimations;
    private volatile boolean containsPlaceholders;

    /*
     *	Constructors
     */

    public HologramLine(@Nullable HologramPage parent, @NonNull Location location, @NotNull String content) {
        super(location);
        this.parent = parent;
        NMS nms = NMS.getInstance();
        this.entityIds[0] = nms.getFreeEntityId();
        this.entityIds[1] = nms.getFreeEntityId();
        this.content = content;
        this.type = HologramLineType.UNKNOWN;
        this.height = 0.3;
        this.parseContent();
    }

    /*
     *	General Methods
     */

    @Override
    public String toString() {
        return "DefaultHologramLine{" +
                "content=" + content +
                "} " + super.toString();
    }

    /**
     * Set the content of the line.
     * <p>
     * This method also parses the content and updates the line.
     * <p>
     * NOTE: The new content can be null but if it is, it will be
     * replaced with an empty string. It is recommended to not use
     * null as content.
     *
     * @param content The new content of the line.
     */
    public void setContent(@Nullable String content) {
        this.content = content == null ? "" : content;
        this.parseContent();
        this.update();
    }

    /**
     * Enable updating and showing to players automatically.
     */
    @Override
    public void enable() {
        super.enable();
        this.show();
    }

    /**
     * Disable updating and showing to players automatically.
     */
    @Override
    public void disable() {
        super.disable();
        this.hide();
    }

    public boolean hasParent() {
        return parent != null;
    }

    @NonNull
    public Location getCenter() {
        Location center = getLocation().clone();
        return hasParent() && parent.getParent().isDownOrigin() ?
                center.add(0, getHeight() / 2, 0) :
                center.subtract(0, getHeight() / 2, 0);
    }

    /**
     * Parse the current content String.
     */
    public void parseContent() {
        HologramLineType prevType = type;
        String contentU = content.toUpperCase(Locale.ROOT);
        if (contentU.startsWith("#ICON:")) {
            type = HologramLineType.ICON;
            if (prevType != type) {
                height = 0.6;
            }
            item = new HologramItem(content.substring("#ICON:".length()));

            containsPlaceholders = PAPI.containsPlaceholders(item.getContent());
        } else if (contentU.startsWith("#SMALLHEAD:")) {
            type = HologramLineType.SMALLHEAD;
            if (prevType != type) {
                height = 0.6;
            }
            item = new HologramItem(content.substring("#SMALLHEAD:".length()));
        } else if (contentU.startsWith("#HEAD:")) {
            type = HologramLineType.HEAD;
            if (prevType != type) {
                height = 0.75;
            }
            item = new HologramItem(content.substring("#HEAD:".length()));
        } else if (contentU.startsWith("#ENTITY:")) {
            type = HologramLineType.ENTITY;
            entity = new HologramEntity(content.substring("#ENTITY:".length()));
            height = NMS.getInstance().getEntityHeight(entity.getType()) + 0.15;
            setOffsetY(-(height + (Version.afterOrEqual(13) ? 0.1 : 0.2)));
            return;
        } else {
            type = HologramLineType.TEXT;
            if (prevType != type) {
                height = 0.3;
            }
            text = content;

            containsAnimations = PLUTO_HOLOGRAMS.getAnimationManager().containsAnimations(text);
            containsPlaceholders = PAPI.containsPlaceholders(text);
        }
        setOffsetY(type.getOffsetY());
    }

    @NonNull
    public Map<String, Object> serializeToMap() {
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("content", content);
        map.put("height", height);
        if (!flags.isEmpty()) map.put("flags", flags.stream().map(EnumFlag::name).collect(Collectors.toList()));
        if (permission != null && !permission.trim().isEmpty()) map.put("permission", permission);
        if (getOffsetX() != 0.0d) map.put("offsetX", offsetX);
        if (getOffsetZ() != 0.0d) map.put("offsetZ", offsetZ);
        if (parent == null || getFacing() != parent.getParent().getFacing()) map.put("facing", facing);
        return map;
    }

    /**
     * Create a new instance of hologram line that's identical to this one.
     *
     * @param location Location of the clone.
     * @return Cloned instance of this line.
     */
    @NonNull
    public HologramLine clone(@Nullable HologramPage parent, @NonNull Location location) {
        HologramLine line = new HologramLine(parent, location, this.getContent());
        line.setHeight(this.getHeight());
        line.setOffsetY(this.getOffsetY());
        line.setOffsetX(this.getOffsetX());
        line.setOffsetZ(this.getOffsetZ());
        line.setFacing(this.getFacing());
        line.setPermission(this.getPermission());
        line.addFlags(this.getFlags().toArray(new EnumFlag[0]));
        return line;
    }

    /**
     * Get the type of this line.
     *
     * @return the type of this line.
     */
    @NonNull
    public HologramLineType getType() {
        return type != null ? type : HologramLineType.UNKNOWN;
    }

    /*
     *	Visibility Methods
     */

    @NotNull
    private String getText(@NonNull Player player, boolean update) {
        if (type != HologramLineType.TEXT) {
            return "";
        }

        UUID uuid = player.getUniqueId();
        String string = playerTextMap.get(uuid);

        // Update cache
        if (update || string == null) {
            string = text == null ? "" : text;
            // Parse placeholders.
            if (!hasFlag(EnumFlag.DISABLE_PLACEHOLDERS)) {
                string = parsePlaceholders(string, player, containsPlaceholders);
            }
            // Update the cached text.
            playerTextMap.put(uuid, string);
        }

        // Parse animations
        if (containsAnimations && !hasFlag(EnumFlag.DISABLE_ANIMATIONS)) {
            string = PLUTO_HOLOGRAMS.getAnimationManager().parseTextAnimations(string);
            // Parse placeholders.
            if (!hasFlag(EnumFlag.DISABLE_PLACEHOLDERS)) {
                // This has been done to allow the use of placeholders in animation frames.
                string = parsePlaceholders(string, player, true);
            }
        }

        return Common.colorize(string);
    }

    @NonNull
    private List<Player> getPlayers(boolean onlyViewers, Player... players) {
        List<Player> playerList;
        if (players == null || players.length == 0) {
            playerList = onlyViewers ? getViewerPlayers() : new ArrayList<>(Bukkit.getOnlinePlayers());
        } else {
            playerList = Arrays.asList(players);
        }
        return playerList;
    }

    @NotNull
    private String parsePlaceholders(@NotNull String string, @NonNull Player player, boolean papi) {
        Preconditions.checkNotNull(parent);

        // Replace internal placeholders.
        string = string.replace("{player}", player.getName());
        string = string.replace("{page}", String.valueOf(hasParent() ? parent.getIndex() + 1 : 1));
        string = string.replace("{pages}", String.valueOf(hasParent() ? parent.getParent().size() : 1));

        // Replace PlaceholderAPI placeholders.
        if (papi) {
            string = PAPI.setPlaceholders(player, string);
            if (string == null) {
                // Some PlacehoderAPI placeholders might be replaced with null, so if the line content
                // is just a single placeholder, there is a possibility that the line will be null. So,
                // if that happens, just replace the null with an empty string.
                string = "";
            }
        }
        return string;
    }

    /**
     * Check if the given player has the permission to see this line, if any.
     *
     * @param player The player.
     * @return True if the player has the permission to see this line, false otherwise.
     */
    public boolean hasPermission(@NonNull Player player) {
        return permission == null || permission.isEmpty() || player.hasPermission(permission);
    }

    /**
     * Update the visibility of this line for the given player. This method checks
     * if the player has the permission to see this line and if they are in the display
     * range. Then it updates the visibility accordingly.
     *
     * @param player The player to update visbility for.
     */
    public void updateVisibility(@NonNull Player player) {
        if (isVisible(player) && !(hasPermission(player) && isInDisplayRange(player))) {
            hide(player);
        } else if (!isVisible(player) && hasPermission(player) && isInDisplayRange(player)) {
            show(player);
        }
    }

    /**
     * Show this line for given players.
     *
     * @param players Given players.
     */
    public void show(Player... players) {
        if (isDisabled()) {
            return;
        }
        List<Player> playerList = getPlayers(false, players);
        NMS nms = NMS.getInstance();
        for (Player player : playerList) {
            if (player == null) {
                continue;
            }
            if (parent != null && parent.getParent().isHideState(player)) {
                continue;
            }
            if (!isVisible(player) && canShow(player) && isInDisplayRange(player)) {
                switch (type) {
                    case TEXT:
                        nms.showFakeEntityArmorStand(player, getLocation(), entityIds[0], true, !HologramLineType.HEAD.equals(type), false);
                        nms.updateFakeEntityCustomName(player, getText(player, true), entityIds[0]);
                        break;
                    case HEAD:
                    case SMALLHEAD:
                        nms.showFakeEntityArmorStand(player, getLocation(), entityIds[0], true, !HologramLineType.HEAD.equals(type), false);
                        ItemStack itemStack = containsPlaceholders ? HologramItem.parseItemStack(item.getContent(), player) : item.parse();
                        nms.helmetFakeEntity(player, itemStack, entityIds[0]);
                        break;
                    case ICON:
                        nms.showFakeEntityArmorStand(player, getLocation(), entityIds[0], true, true, false);
                        ItemStack itemStack1 = containsPlaceholders ? HologramItem.parseItemStack(item.getContent(), player) : item.parse();
                        nms.showFakeEntityItem(player, getLocation(), itemStack1, entityIds[1]);
                        nms.attachFakeEntity(player, entityIds[0], entityIds[1]);
                        break;
                    case ENTITY:
                        EntityType entityType = new HologramEntity(PAPI.setPlaceholders(player, getEntity().getContent())).getType();
                        if (entityType == null || !PlutoEntityType.isAllowed(entityType)) break;
                        nms.showFakeEntityArmorStand(player, getLocation(), entityIds[0], true, true, false);

                        if (entity.getType().isAlive()) {
                            nms.showFakeEntityLiving(player, getLocation(), entityType, entityIds[1]);
                        } else {
                            nms.showFakeEntity(player, getLocation(), entityType, entityIds[1]);
                        }
                        nms.attachFakeEntity(player, entityIds[0], entityIds[1]);
                        break;
                    default:
                        break;
                }
                viewers.add(player.getUniqueId());
            }
        }
    }

    /**
     * Update this line for given players.
     *
     * @param players Given players.
     */
    public void update(Player... players) {
        if (isDisabled() || hasFlag(EnumFlag.DISABLE_UPDATING))
            return;

        List<Player> playerList = getPlayers(true, players);
        NMS nms = NMS.getInstance();
        for (Player player : playerList) {
            updateVisibility(player);

            if (!isVisible(player) || !isInUpdateRange(player))
                continue;

            if (type == HologramLineType.TEXT) {
                UUID uuid = player.getUniqueId();
                String lastText = lastTextMap.get(uuid);
                String text = getText(player, true);
                if (!text.equals(lastText)) {
                    lastTextMap.put(uuid, text);
                    nms.updateFakeEntityCustomName(player, text, entityIds[0]);
                }
            } else if (type == HologramLineType.HEAD || type == HologramLineType.SMALLHEAD)
                nms.helmetFakeEntity(player, HologramItem.parseItemStack(getItem().getContent(), player), entityIds[0]);
        }
    }

    /**
     * Update the location of this line for given players.
     *
     * @param players Given players.
     */
    public void updateLocation(boolean updateRotation, Player... players) {
        if (isDisabled())
            return;
        List<Player> playerList = getPlayers(true, players);
        for (Player player : playerList) {
            if (!isVisible(player) || !isInUpdateRange(player))
                continue;
            if (type == HologramLineType.ENTITY && updateRotation) {
                this.hide();
                this.show();
            } else
                NMS.getInstance().teleportFakeEntity(player, getLocation(), entityIds[0]);
        }
    }

    public void updateAnimations(Player... players) {
        if (isDisabled() || hasFlag(EnumFlag.DISABLE_ANIMATIONS))
            return;
        List<Player> playerList = getPlayers(true, players);
        NMS nms = NMS.getInstance();
        for (Player player : playerList) {
            if (!isVisible(player) || !isInUpdateRange(player))
                continue;
            if (type == HologramLineType.TEXT) {
                UUID uuid = player.getUniqueId();
                String lastText = lastTextMap.get(uuid);
                String text = getText(player, false);
                if (!text.equals(lastText)) {
                    lastTextMap.put(uuid, text);
                    nms.updateFakeEntityCustomName(player, text, entityIds[0]);
                }
            }
        }
    }

    /**
     * Hide this line for given players.
     *
     * @param players Given players.
     */
    public void hide(Player... players) {
        List<Player> playerList = getPlayers(true, players);
        for (Player player : playerList)
            if (isVisible(player)) {
                NMS.getInstance().hideFakeEntities(player, entityIds[0], entityIds[1]);
                viewers.remove(player.getUniqueId());
            }
    }

    public boolean isInDisplayRange(@NonNull Player player) {
        return parent == null || parent.getParent().isInDisplayRange(player);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInUpdateRange(@NonNull Player player) {
        return parent == null || parent.getParent().isInDisplayRange(player);
    }

    public double getOffsetX() {
        return offsetX.get();
    }

    public double getOffsetY() {
        return offsetY.get();
    }

    public double getOffsetZ() {
        return offsetZ.get();
    }

    public void setOffsetX(double offsetX) {
        this.offsetX.set(offsetX);
    }

    public void setOffsetY(double offsetY) {
        this.offsetY.set(offsetY);
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ.set(offsetZ);
    }

    /*
     *	Override Methods
     */

    @Override
    public boolean hasFlag(@NonNull EnumFlag flag) {
        return super.hasFlag(flag) || (parent != null && parent.getParent().hasFlag(flag));
    }

    @Override
    public boolean canShow(@NonNull Player player) {
        return super.canShow(player) && (parent == null || parent.getParent().canShow(player));
    }

}