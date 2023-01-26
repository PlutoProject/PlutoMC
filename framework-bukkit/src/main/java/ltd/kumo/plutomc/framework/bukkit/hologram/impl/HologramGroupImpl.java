package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.Hologram;
import ltd.kumo.plutomc.framework.bukkit.hologram.HologramGroup;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HologramGroupImpl implements HologramGroup {

    private final BukkitPlatform platform;
    private Location location;
    private final List<Hologram> holograms = new ArrayList<>();

    private boolean dropped;

    public HologramGroupImpl(BukkitPlatform platform, Location location) {
        this.platform = platform;
        this.location = location;
    }

    @Override
    public @NotNull Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(@NotNull Location location) {
        if (this.isDropped())
            return;
        Preconditions.checkNotNull(location);
        this.location = location;
        Location nextLocation = this.location.clone();
        for (Hologram hologram : this.holograms) {
            hologram.setLocation(nextLocation);
            nextLocation.subtract(0, hologram.getHeight() + 0.1, 0);
        }
    }

    @Override
    public void show() {
        if (this.isDropped())
            return;
        this.setLocation(this.location);
        this.holograms.forEach(Hologram::show);
    }

    @Override
    public void hide() {
        if (this.isDropped())
            return;
        this.holograms.forEach(Hologram::hide);
    }

    @Override
    public void refresh(BukkitPlayer player) {
        if (this.isDropped())
            return;
        this.holograms.forEach(hologram -> hologram.refresh(player));
    }

    @Override
    public void refreshAll() {
        if (this.isDropped())
            return;
        this.setLocation(this.location);
        this.holograms.forEach(Hologram::refreshAll);
    }

    @Override
    public void drop() {
        if (this.isDropped())
            return;
        this.holograms.forEach(Hologram::drop);
        this.dropped = true;
    }

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    @Override
    public void addHologram(@NotNull Hologram hologram) {
        if (this.isDropped())
            return;
        if (this.holograms.contains(hologram))
            return;
        this.holograms.add(hologram);
        this.setLocation(this.location);
    }

    @Override
    public void addHologramAll(@NotNull HologramGroup group) {
        if (this.isDropped())
            return;
        boolean notContains = false;
        for (Hologram hologram : group) {
            if (this.holograms.contains(hologram))
                continue;
            this.holograms.add(hologram);
            notContains = true;
        }
        if (!notContains)
            return;
        this.setLocation(this.location);
    }

    @Override
    public void removeHologram(@NotNull Hologram hologram) {
        if (this.isDropped())
            return;
        if (!this.holograms.contains(hologram))
            return;
        this.holograms.remove(hologram);
        this.setLocation(this.location);
    }

    @Override
    public void removeHologramAll(@NotNull HologramGroup group) {
        if (this.isDropped())
            return;
        boolean contains = false;
        for (Hologram hologram : group) {
            if (!this.holograms.contains(hologram))
                continue;
            this.holograms.remove(hologram);
            contains = true;
        }
        if (!contains)
            return;
        this.setLocation(this.location);
    }

    @Override
    public void addViewer(@NotNull BukkitPlayer player) {
        if (this.isDropped())
            return;
        this.holograms.forEach(hologram -> hologram.addViewer(player));
    }

    @Override
    public void removeViewer(@NotNull BukkitPlayer player) {
        if (this.isDropped())
            return;
        this.holograms.forEach(hologram -> hologram.removeViewer(player));
    }

    @Override
    public List<Hologram> listHolograms() {
        return new ArrayList<>(this.holograms);
    }

    @NotNull
    @Override
    public Iterator<Hologram> iterator() {
        return listHolograms().iterator();
    }

}
