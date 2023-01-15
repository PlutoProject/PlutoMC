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

    public static boolean isOnSomething(@NotNull Location location, @NotNull Collection<Material> materials) {
        Objects.requireNonNull(location);
        Objects.requireNonNull(materials);
        return materials.contains(getUnder(location).getBlock().getType());
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
