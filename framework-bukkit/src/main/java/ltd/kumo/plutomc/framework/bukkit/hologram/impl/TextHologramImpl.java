package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.HologramReflections;
import ltd.kumo.plutomc.framework.bukkit.hologram.TextHologram;
import ltd.kumo.plutomc.framework.bukkit.injector.ProtocolInjector;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class TextHologramImpl implements TextHologram {

    private final BukkitPlatform platform;

    private final Set<BukkitPlayer> players = new HashSet<>();
    private Location location;
    private boolean dropped = false;
    private final Map<BukkitPlayer, Location> originalLocations = new HashMap<>();
    private Function<BukkitPlayer, Component> function = (player) -> Component.text("Hello, world!");

    private final Object entity;

    private boolean showed = true;

    public TextHologramImpl(BukkitPlatform platform, Location location) {
        Preconditions.checkNotNull(platform);
        Preconditions.checkNotNull(location);
        Preconditions.checkNotNull(location.getWorld());
        this.platform = platform;
        this.location = location;
        this.entity = HologramReflections.CONSTRUCTOR_ENTITY_ARMOR_STAND.newInstance(HologramReflections.METHOD_GET_HANDLE_WORLD.invoke(location.getWorld()), location.x(), location.y(), location.z());
        HologramReflections.METHOD_SET_NO_GRAVITY.invoke(this.entity, true);
        HologramReflections.METHOD_SET_CUSTOM_NAME_VISIBLE.invoke(this.entity, true);
        HologramReflections.METHOD_SET_ARMS.invoke(this.entity, false);
        HologramReflections.METHOD_SET_HIDE_BASE_PLATE.invoke(this.entity, true);
        HologramReflections.METHOD_SET_INVISIBLE.invoke(this.entity, 5, true);
        HologramReflections.METHOD_SET_SMALL.invoke(this.entity, true);
        HologramReflections.METHOD_TELEPORT.invoke(this.entity, location.x(), location.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity), location.z(), location.getYaw(), location.getPitch());
    }

    @Override
    public void show() {
        if (this.isDropped())
            return;
        if (showed)
            return;
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY.newInstance(HologramReflections.FIELD_ENTITY_ID.get(this.entity),
                HologramReflections.FIELD_ENTITY_UUID.get(this.entity),
                this.location.x(),
                this.location.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity),
                this.location.z(),
                this.location.getPitch(),
                this.location.getYaw(),
                HologramReflections.FIELD_ENTITY_TYPE_ARMOR_STAND.getStatic(),
                0,
                HologramReflections.FIELD_ENTITY_VELOCITY.get(this.entity),
                this.location.getYaw());
        for (BukkitPlayer player : this.players) {
            injector.sendPacket(player.player(), packet);
        }
        showed = true;
        refreshAll();
    }

    @Override
    public void hide() {
        if (this.isDropped())
            return;
        if (!showed)
            return;
        if (this.players.isEmpty())
            return;
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY.newInstance(new Object[] { new int[] { (int) HologramReflections.FIELD_ENTITY_ID.get(this.entity) } });
        for (BukkitPlayer player : this.players) {
            injector.sendPacket(player.player(), packet);
        }
        showed = false;
    }

    @Override
    public void refreshAll() {
        if (this.isDropped())
            return;
        if (!this.showed)
            return;
        this.players.forEach(this::refresh);
    }

    @Override
    public void refresh(@NotNull BukkitPlayer player) {
        if (this.isDropped())
            return;
        if (!this.showed)
            return;
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        // 这里如果viewer里没有玩家不要忽略，要给玩家发EntityDestroy包，因为可能是要移除玩家
        if (this.players.contains(player)) {
            Component text = this.function.apply(player);
            HologramReflections.METHOD_SET_CUSTOM_NAME.invoke(this.entity, HologramReflections.METHOD_AS_VANILLA.invokeStatic(text));
            Object list = HologramReflections.METHOD_PACK_ALL.invoke(HologramReflections.FIELD_DATA_WATCHER.get(this.entity));
            Object metadataPacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA.newInstance(((int) HologramReflections.FIELD_ENTITY_ID.get(this.entity)), list == null ? new ArrayList<>() : (List<?>) list);
            injector.sendPacket(player.player(), metadataPacket);
            Location originalLocation = this.originalLocations.get(player);
            if (originalLocation == null) {
                injector.sendPacket(player.player(), HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY.newInstance(new Object[] { new int[] { (int) HologramReflections.FIELD_ENTITY_ID.get(this.entity) } }));
                this.originalLocations.put(player, this.location);
                this.refresh(player);
                return;
            }
            if (!Objects.equals(this.location, originalLocation)) {
                if (Math.abs(this.location.x() - originalLocation.x()) > 8 || Math.abs(this.location.y() - originalLocation.y()) > 8 || Math.abs(this.location.z() - originalLocation.z()) > 8) {
                    Object teleportPacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT.newInstance(this.entity);
                    injector.sendPacket(player.player(), teleportPacket);
                } else {
                    Object movePacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE.newInstance(HologramReflections.FIELD_ENTITY_ID.get(this.entity),
                            calculateDelta(location.x(), originalLocation.x()),
                            calculateDelta(location.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity), originalLocation.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity)),
                            calculateDelta(location.z(), originalLocation.z()),
                            false);
                    injector.sendPacket(player.player(), movePacket);
                }
            }
            this.originalLocations.put(player, this.location);
        } else {
            injector.sendPacket(player.player(), HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY.newInstance(new Object[] { new int[] { (int) HologramReflections.FIELD_ENTITY_ID.get(this.entity) } }));
        }
    }

    @Override
    public double getHeight() {
        return 0.2;
    }

    @Override
    public @NotNull Function<BukkitPlayer, Component> getText() {
        return this.function;
    }

    @Override
    public void setText(@NotNull Function<BukkitPlayer, Component> text) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(text);
        this.function = text;
    }

    @Override
    public final @NotNull Location getLocation() {
        return this.location;
    }

    @Override
    public final void setLocation(@NotNull Location location) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(location);
        this.location = location;
        HologramReflections.METHOD_TELEPORT.invoke(this.entity, location.x(), location.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity), location.z(), location.getYaw(), location.getPitch());
        refreshAll();
    }

    @Override
    public final void drop() {
        if (this.isDropped())
            return;
        this.players.forEach(this::removeViewer);
        this.dropped = true;
    }

    @Override
    public final boolean isDropped() {
        return this.dropped;
    }

    @Override
    public void addViewer(@NotNull BukkitPlayer player) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(player);
        if (this.players.contains(player))
            return;
        this.players.add(player);
        this.originalLocations.put(player, this.location);

        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY.newInstance(HologramReflections.FIELD_ENTITY_ID.get(this.entity),
                HologramReflections.FIELD_ENTITY_UUID.get(this.entity),
                this.location.x(),
                this.location.y() - (float) HologramReflections.METHOD_GET_HEIGHT.invoke(this.entity),
                this.location.z(),
                this.location.getPitch(),
                this.location.getYaw(),
                HologramReflections.FIELD_ENTITY_TYPE_ARMOR_STAND.getStatic(),
                0,
                HologramReflections.FIELD_ENTITY_VELOCITY.get(this.entity),
                this.location.getYaw());
        injector.sendPacket(player.player(), packet);
        this.refresh(player);
    }

    @Override
    public void removeViewer(@NotNull BukkitPlayer player) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(player);
        if (!this.players.contains(player))
            return;
        this.players.remove(player);
        this.originalLocations.remove(player);
        this.refresh(player);
    }

    @Override
    public boolean hasViewer(@NotNull BukkitPlayer player) {
        Preconditions.checkNotNull(player);
        return this.players.contains(player);
    }

    @Override
    public @NotNull Collection<BukkitPlayer> listViewers() {
        return new HashSet<>(this.players);
    }

    private static short calculateDelta(double current, double prev) {
        return (short) ((current * 32 - prev * 32) * 128);
    }

}
