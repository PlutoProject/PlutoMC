package ltd.kumo.plutomc.modules.ironelevator.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public final class LocationUtility {

    private LocationUtility() {
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isOnSomething(@NotNull Location location, @NotNull Collection<Material> materials) {
        Objects.requireNonNull(location);
        Objects.requireNonNull(materials);
        return materials.contains(getUnder(location).getBlock().getType());
    }

    @NotNull
    public static Location getUnder(@NotNull Location location) {
        Objects.requireNonNull(location);
        return clone(location).subtract(0, 1, 0);
    }

    @NotNull
    public static Location getAbove(@NotNull Location location) {
        Objects.requireNonNull(location);
        return clone(location).add(0, 1, 0);
    }

    @NotNull
    public static Location clone(@NotNull Location location) {
        Objects.requireNonNull(location);
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

}
