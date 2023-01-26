package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.*;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class BukkitHologramService implements HologramService {

    private final BukkitPlatform platform;
    private final Map<Class<?>, BiFunction<BukkitPlatform, Location, Hologram>> holograms = new HashMap<>();

    public BukkitHologramService(BukkitPlatform platform) {
        this.platform = platform;

        this.holograms.put(TextHologram.class, TextHologramImpl::new);
        this.holograms.put(TextHologramImpl.class, TextHologramImpl::new);
        this.holograms.put(SimplifiedTextHologram.class, SimplifiedTextHologramImpl::new);
        this.holograms.put(SimplifiedTextHologramImpl.class, SimplifiedTextHologramImpl::new);
        this.holograms.put(ItemHologram.class, ItemHologramImpl::new);
        this.holograms.put(ItemHologramImpl.class, ItemHologramImpl::new);
        this.holograms.put(SimplifiedItemHologram.class, SimplifiedItemHologramImpl::new);
        this.holograms.put(SimplifiedItemHologramImpl.class, SimplifiedItemHologramImpl::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Hologram> T createHologram(Class<T> type, Location location) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(location);
        location.setYaw(0);
        location.setPitch(0);
        if (!this.holograms.containsKey(type))
            throw new RuntimeException("Hologram type not exists");
        Preconditions.checkNotNull(this.holograms.get(type));
        return (T) this.holograms.get(type).apply(this.platform, location);
    }

    @Override
    public HologramGroup createGroup(Location location) {
        return null;
    }

}
