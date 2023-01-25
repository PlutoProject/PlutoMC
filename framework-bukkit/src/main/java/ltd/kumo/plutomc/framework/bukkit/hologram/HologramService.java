package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.shared.Service;
import org.bukkit.Location;

public interface HologramService extends Service<HologramService> {

    <T extends Hologram> T createHologram(Class<T> type, Location location);

    HologramGroup createGroup(Location location);

}
