package ltd.kumo.plutomc.modules.ironelevator.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public final class LocationUtility {

    private LocationUtility() {
    }

    @NotNull
    public static Location cleanedLocation(@NotNull Location location) {
        Objects.requireNonNull(location);
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static boolean isOnSomething(@NotNull Location location, @NotNull Material material) {
        Objects.requireNonNull(location);
        Objects.requireNonNull(material);
        return getUnder(location).getBlock().getType().equals(material);
    }

    @NotNull
    public static Location getUnder(@NotNull Location location) {
        Objects.requireNonNull(location);
        return location.clone().subtract(0, 1, 0);
    }

    @NotNull
    public static Location getAbove(@NotNull Location location) {
        Objects.requireNonNull(location);
        return location.clone().add(0, 1, 0);
    }

}
