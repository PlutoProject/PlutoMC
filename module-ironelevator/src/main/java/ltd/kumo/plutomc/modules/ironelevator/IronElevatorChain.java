package ltd.kumo.plutomc.modules.ironelevator;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.modules.ironelevator.utilities.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class IronElevatorChain {

    @NotNull
    private final ImmutableList<Location> LOCATIONS;

    private IronElevatorChain(@NotNull Location location) {
        this.LOCATIONS = searchLocsFrom(location);

        if (!oneOrMore())
            throw new RuntimeException("Less than one legal block!");
    }

    public static IronElevatorChain from(@NotNull Location location) {
        return new IronElevatorChain(location);
    }

    @NotNull
    private static ImmutableList<Location> searchLocsFrom(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull List<Location> result = new ArrayList<>();

        for (int index = location.getWorld().getMinHeight();
             index < location.getWorld().getMaxHeight(); index++) {
            @NotNull Location indexLocation = new Location(
                    location.getWorld(),
                    location.getBlockX(),
                    index,
                    location.getBlockZ()
            );

            if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(indexLocation.getBlock().getType()))
                continue;

            @NotNull Location above1 = LocationUtility.getAbove(indexLocation);
            @NotNull Location above2 = LocationUtility.getAbove(above1);

            if (!above1.getBlock().getType().equals(Material.AIR)
                    || !above2.getBlock().getType().equals(Material.AIR))
                continue;

            result.add(indexLocation);
        }

        return ImmutableList.copyOf(result);
    }

    private boolean oneOrMore() {
        return LOCATIONS.size() >= 1;
    }

    public int getNextFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = location.toBlockLocation();

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType()))
            throw new RuntimeException("Not on a legal elevator block!");

        @NotNull Location elevatorBlockLocation = LocationUtility.getUnder(cleanedLocation);

        if (LOCATIONS.size() != LOCATIONS.indexOf(elevatorBlockLocation) + 1)
            return LOCATIONS.indexOf(elevatorBlockLocation) + 2;

        return -1;
    }

    @NotNull
    public Location getNextFloor(@NotNull Location location) {
        if (getNextFloorNumber(location) == -1)
            throw new RuntimeException("No more floors!");

        return LocationUtility.getUnder(LOCATIONS.get(getNextFloorNumber(location) - 1)); // 因为LOCATIONS创建的时候里面就全是BlockX Y Z了所以不需要clean
    }

    public int getPreviousFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = location.toBlockLocation();

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType()))
            throw new RuntimeException("Not on a legal elevator block!");

        @NotNull Location elevatorBlockLocation = LocationUtility.getUnder(cleanedLocation);

        if (LOCATIONS.indexOf(elevatorBlockLocation) != 0)
            return LOCATIONS.indexOf(elevatorBlockLocation);

        return -1;
    }

    @NotNull
    public Location getPreviousFloor(@NotNull Location location) {
        if (getPreviousFloorNumber(location) == -1)
            throw new RuntimeException("No more floors!");

        return LocationUtility.getAbove(LOCATIONS.get(getPreviousFloorNumber(location) - 1)); // 参考getNextFlour的注释
    }

    public int getCurrentFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = location.toBlockLocation();

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType()))
            throw new RuntimeException("Not on a legal elevator block!");

        @NotNull Location elevatorBlockLocation = LocationUtility.getUnder(cleanedLocation);

        if (LOCATIONS.indexOf(elevatorBlockLocation) != 0)
            return LOCATIONS.indexOf(elevatorBlockLocation) + 1;

        return -1;
    }

    @NotNull
    public Location getCurrentFloor(@NotNull Location location) {
        if (getCurrentFloorNumber(location) == -1)
            throw new RuntimeException("No more floors!");

        return LocationUtility.getAbove(LOCATIONS.get(getCurrentFloorNumber(location) - 1)); // 参考getNextFlour的注释
    }

    public int getFloorCount() {
        return LOCATIONS.size();
    }

}
