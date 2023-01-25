package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.Hologram;
import ltd.kumo.plutomc.framework.bukkit.hologram.HologramGroup;
import ltd.kumo.plutomc.framework.bukkit.hologram.HologramService;
import ltd.kumo.plutomc.framework.bukkit.hologram.TextHologram;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BukkitHologramService implements HologramService {

    private final BukkitPlatform platform;
    private final Map<Class<?>, Function<Location, Hologram>> holograms = new HashMap<>();

    public BukkitHologramService(BukkitPlatform platform) {
        this.platform = platform;

        this.holograms.put(TextHologram.class, location -> new TextHologramImpl(this.platform, location));
        this.holograms.put(TextHologramImpl.class, location -> new TextHologramImpl(this.platform, location));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Hologram> T createHologram(Class<T> type, Location location) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(location);
        if (!this.holograms.containsKey(type))
            throw new RuntimeException("Hologram type not exists");
        Preconditions.checkNotNull(this.holograms.get(type));
        return (T) this.holograms.get(type).apply(location);
    }

    @Override
    public HologramGroup createGroup(Location location) {
        return null;
    }

}
