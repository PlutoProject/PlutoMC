package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.HologramReflections;
import ltd.kumo.plutomc.framework.bukkit.hologram.ItemHologram;
import ltd.kumo.plutomc.framework.bukkit.injector.ProtocolInjector;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class ItemHologramImpl implements ItemHologram {

    private final BukkitPlatform platform;

    private final Set<BukkitPlayer> players = new HashSet<>();
    private final Map<BukkitPlayer, Location> originalLocations = new HashMap<>();
    private final Object entity;
    private final Item bukkitEntity;
    private Location location;
    private boolean dropped = false;
    private Function<BukkitPlayer, ItemStack> function = (player) -> new ItemStack(Material.AIR);
    private boolean showed = true;

    public ItemHologramImpl(BukkitPlatform platform, Location location) {
        Preconditions.checkNotNull(platform);
        Preconditions.checkNotNull(location);
        Preconditions.checkNotNull(location.getWorld());
        this.platform = platform;
        this.location = location;
        this.entity = HologramReflections.CONSTRUCTOR_ENTITY_ITEM.newInstance(HologramReflections.METHOD_GET_HANDLE_WORLD.invoke(location.getWorld()), location.x(), location.y(), location.z(), HologramReflections.METHOD_AS_NMS_COPY.invokeStatic(new ItemStack(Material.AIR)));
        this.bukkitEntity = (Item) HologramReflections.METHOD_GET_CRAFT_ENTITY.invoke(this.entity);
        this.bukkitEntity.setUnlimitedLifetime(true);
        this.bukkitEntity.setCanMobPickup(false);
        this.bukkitEntity.setCanPlayerPickup(false);
        this.bukkitEntity.setHealth(32767);
        this.bukkitEntity.setGravity(false);
        // HologramReflections.FIELD_ITEM_LIFETIME.set(this.entity, -32768);
        // HologramReflections.FIELD_ITEM_PICKUP_DELAY.set(this.entity, 32767);
        // HologramReflections.FIELD_ITEM_HEALTH.set(this.entity, 32767);
        // HologramReflections.FIELD_ITEM_CAN_MOB_PICKUP.set(this.entity, false);
        // HologramReflections.METHOD_SET_INVISIBLE.invoke(this.entity, 5, true);
        // HologramReflections.METHOD_SET_NO_GRAVITY.invoke(this.entity, true);
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
                // HologramReflections.FIELD_ENTITY_TYPE_ITEM.getStatic(),
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
        return 0.3;
    }

    @Override
    public @NotNull Function<BukkitPlayer, ItemStack> getItem() {
        return this.function;
    }

    @Override
    public void setItem(@NotNull Function<BukkitPlayer, ItemStack> item) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(item);
        this.function = item;
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
                // HologramReflections.FIELD_ENTITY_TYPE_ITEM.getStatic(),
                HologramReflections.METHOD_GET_ENTITY_TYPES.invoke(this.entity),
                0,
                HologramReflections.FIELD_ENTITY_VELOCITY.get(this.entity),
                this.location.getYaw());
        injector.sendPacket(player.player(), packet);
        updateMetadata(player, injector);
    }

    private void updateMetadata(BukkitPlayer player, ProtocolInjector injector) {
        ItemStack itemStack = this.function.apply(player);
        HologramReflections.METHOD_SET_ITEM_STACK.invoke(this.entity, HologramReflections.METHOD_AS_NMS_COPY.invokeStatic(itemStack));
        Object list = HologramReflections.METHOD_PACK_ALL.invoke(HologramReflections.FIELD_DATA_WATCHER.get(this.entity));
        Object metadataPacket = HologramReflections.CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA.newInstance(this.bukkitEntity.getEntityId(), list == null ? new ArrayList<>() : list);
        injector.sendPacket(player.player(), metadataPacket);
    }

}
