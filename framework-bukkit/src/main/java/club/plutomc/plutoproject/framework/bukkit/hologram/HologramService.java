package club.plutomc.plutoproject.framework.bukkit.hologram;

import club.plutomc.plutoproject.framework.shared.Service;
import org.bukkit.Location;

public interface HologramService extends Service<HologramService> {

    <T extends Hologram> T createHologram(Class<T> type, Location location);

    HologramGroup createGroup(Location location);

}
