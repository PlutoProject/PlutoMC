package club.plutomc.plutoproject.framework.bukkit.hologram.impl;

import club.plutomc.plutoproject.framework.bukkit.BukkitPlatform;
import club.plutomc.plutoproject.framework.bukkit.hologram.TextHologram;
import com.google.common.base.Preconditions;
import club.plutomc.plutoproject.framework.bukkit.hologram.HologramReflections;
import club.plutomc.plutoproject.framework.bukkit.injector.ProtocolInjector;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class TextHologramImpl implements TextHologram {

    private final BukkitPlatform platform;

    private final Set<BukkitPlayer> players = new HashSet<>();
    private final Map<BukkitPlayer, Location> originalLocations = new HashMap<>();
    private final Object entity;
    private final ArmorStand bukkitEntity;
    private Location location;
    private boolean dropped = false;
    private Function<BukkitPlayer, Component> function = (player) -> Component.text("Hello, world!");
    // private final Turtle bukkitEntity;
    private boolean showed = true;

    public TextHologramImpl(BukkitPlatform platform, Location location) {
        Preconditions.checkNotNull(platform);
        Preconditions.checkNotNull(location);
        Preconditions.checkNotNull(location.getWorld());
        this.platform = platform;
        this.location = location;
        this.entity = HologramReflections.CONSTRUCTOR_ENTITY_ARMOR_STAND.newInstance(HologramReflections.METHOD_GET_HANDLE_WORLD.invoke(location.getWorld()), location.x(), location.y(), location.z());

        this.bukkitEntity = (ArmorStand) HologramReflections.METHOD_GET_CRAFT_ENTITY.invoke(this.entity);
        this.bukkitEntity.setGravity(false);
        this.bukkitEntity.setCustomNameVisible(true);
        this.bukkitEntity.setArms(false);
        this.bukkitEntity.setInvisible(true);
        this.bukkitEntity.setSmall(true);
        // HologramReflections.METHOD_SET_NO_GRAVITY.invoke(this.entity, true);
        // HologramReflections.METHOD_SET_CUSTOM_NAME_VISIBLE.invoke(this.entity, true);
        // HologramReflections.METHOD_SET_ARMS.invoke(this.entity, false);
        // HologramReflections.METHOD_SET_HIDE_BASE_PLATE.invoke(this.entity, true);
        // HologramReflections.METHOD_SET_INVISIBLE.invoke(this.entity, 5, true);
        // HologramReflections.METHOD_SET_SMALL.invoke(this.entity, true);
        HologramReflections.METHOD_TELEPORT.invoke(this.entity, location.x(), location.y() - this.bukkitEntity.getHeight(), location.z(), location.getYaw(), location.getPitch());
    }

    private static short calculateDelta(double current, double prev) {
        return (short) ((current * 32 - prev * 32) * 128);
    }

    @Override
    public void show() {
        if (this.isDropped())
            return;
        if (showed)
            return;
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY.newInstance(this.bukkitEntity.getEntityId(),
                HologramReflections.FIELD_ENTITY_UUID.get(this.entity),
                this.location.x(),
                this.location.y() - this.bukkitEntity.getHeight(),
                this.location.z(),
                this.location.getPitch(),
                this.location.getYaw(),
                // HologramReflections.FIELD_ENTITY_TYPE_ARMOR_STAND.getStatic(),
                HologramReflections.METHOD_GET_ENTITY_TYPES.invoke(this.entity),
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
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY.newInstance(new Object[]{new int[]{this.bukkitEntity.getEntityId()}});
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
        if (!this.players.contains(player))
            return;
        updateMetadata(player, injector);
        Location originalLocation = this.originalLocations.get(player);
        if (originalLocation == null) {
            this.originalLocations.put(player, this.location);
            this.refresh(player);
            return;
        }
        if (!Objects.equals(this.location, originalLocation)) {
            if (Math.abs(this.location.x() - originalLocation.x()) > 8 || Math.abs(this.location.y() - originalLocation.y()) > 8 || Math.abs(this.location.z() - originalLocation.z()) > 8) {
                Object teleportPacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT.newInstance(this.entity);
                injector.sendPacket(player.player(), teleportPacket);
            } else {
                Object movePacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE.newInstance(this.bukkitEntity.getEntityId(),
                        calculateDelta(location.x(), originalLocation.x()),
                        calculateDelta(location.y() - this.bukkitEntity.getHeight(), originalLocation.y() - this.bukkitEntity.getHeight()),
                        calculateDelta(location.z(), originalLocation.z()),
                        true);
                injector.sendPacket(player.player(), movePacket);
            }
        } else {
            destroy(player);
            spawn(player);
        }
        this.originalLocations.put(player, this.location);
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
        HologramReflections.METHOD_TELEPORT.invoke(this.entity, location.x(), location.y() - this.bukkitEntity.getHeight(), location.z(), location.getYaw(), location.getPitch());
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

        spawn(player);
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
        this.destroy(player);
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

    private void destroy(BukkitPlayer player) {
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY.newInstance(new Object[]{new int[]{this.bukkitEntity.getEntityId()}});
        injector.sendPacket(player.player(), packet);
    }

    private void spawn(BukkitPlayer player) {
        ProtocolInjector injector = this.platform.getService(ProtocolInjector.class);
        Object packet = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY.newInstance(this.bukkitEntity.getEntityId(),
                HologramReflections.FIELD_ENTITY_UUID.get(this.entity),
                this.location.x(),
                this.location.y() - this.bukkitEntity.getHeight(),
                this.location.z(),
                this.location.getPitch(),
                this.location.getYaw(),
                // HologramReflections.FIELD_ENTITY_TYPE_ARMOR_STAND.getStatic(),
                HologramReflections.METHOD_GET_ENTITY_TYPES.invoke(this.entity),
                0,
                HologramReflections.FIELD_ENTITY_VELOCITY.get(this.entity),
                this.location.getYaw());
        injector.sendPacket(player.player(), packet);
        updateMetadata(player, injector);
    }

    private void updateMetadata(BukkitPlayer player, ProtocolInjector injector) {
        Component text = this.function.apply(player);
        HologramReflections.METHOD_SET_CUSTOM_NAME.invoke(this.entity, HologramReflections.METHOD_AS_VANILLA.invokeStatic(text));
        Object list = HologramReflections.METHOD_PACK_ALL.invoke(HologramReflections.FIELD_DATA_WATCHER.get(this.entity));
        Object metadataPacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA.newInstance(this.bukkitEntity.getEntityId(), list == null ? new ArrayList<>() : list);
        injector.sendPacket(player.player(), metadataPacket);
    }

}
