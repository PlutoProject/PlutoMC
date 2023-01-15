package ltd.kumo.plutomc.modules.ironelevator;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.modules.ironelevator.utilites.LocationUtility;
import org.bukkit.Chunk;
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

        if (!oneOrMore()) {
            throw new RuntimeException("Less than one legal block!");
        }
    }

    public static IronElevatorChain from(@NotNull Location location) {
        return new IronElevatorChain(location);
    }

    @NotNull
    private static ImmutableList<Location> searchLocsFrom(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull List<Location> result = new ArrayList<>();
        @NotNull Chunk chunk = location.getChunk();

        int index = location.getWorld().getMinHeight();
        while (index < location.getWorld().getMaxHeight()) {
            @NotNull Location indexLocation = new Location(
                    location.getWorld(),
                    location.getBlockX(),
                    index,
                    location.getBlockZ()
            );

            if (IronElevatorModule.ELEVATOR_MATERIALS.contains(indexLocation.getBlock().getType())) {
                @NotNull Location above1 = new Location(indexLocation.getWorld(),
                        indexLocation.getBlockX(),
                        indexLocation.getBlockY() + 1,
                        indexLocation.getBlockZ());
                @NotNull Location above2 = new Location(indexLocation.getWorld(),
                        indexLocation.getBlockX(),
                        indexLocation.getBlockY() + 2,
                        indexLocation.getBlockZ());

                if (above1.getBlock().getType().equals(Material.AIR)
                        && above2.getBlock().getType().equals(Material.AIR)) {
                    result.add(indexLocation);
                }
            }

            index++;
        }

        return ImmutableList.copyOf(result);
    }

    private boolean oneOrMore() {
        return LOCATIONS.size() >= 1;
    }

    public int getNextFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = LocationUtility.cleanedLocation(location);

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType())) {
            throw new RuntimeException("Not on a legal elevator block!");
        }

        @NotNull Location elevatorBlockLocation = new Location(location.getWorld(),
                location.getBlockX(),
                location.getBlockY() - 1,
                location.getBlockZ());

        if (LOCATIONS.size() != LOCATIONS.indexOf(elevatorBlockLocation) + 1) {
            return LOCATIONS.indexOf(elevatorBlockLocation) + 2;
        }

        return -1;
    }

    @NotNull
    public Location getNextFloor(@NotNull Location location) {
        if (getNextFloorNumber(location) == -1) {
            throw new RuntimeException("No more floors!");
        }

        @NotNull Location nextFloorBlock = LOCATIONS.get(getNextFloorNumber(location) - 1);
        return new Location(nextFloorBlock.getWorld(),
                nextFloorBlock.getBlockX(),
                nextFloorBlock.getBlockY() + 1,
                nextFloorBlock.getBlockZ());
    }

    public int getPreviousFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = LocationUtility.cleanedLocation(location);

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType())) {
            throw new RuntimeException("Not on a legal elevator block!");
        }

        @NotNull Location elevatorBlockLocation = new Location(location.getWorld(),
                location.getBlockX(),
                location.getBlockY() - 1,
                location.getBlockZ());

        if (LOCATIONS.indexOf(elevatorBlockLocation) != 0) {
            return LOCATIONS.indexOf(elevatorBlockLocation);
        }

        return -1;
    }

    @NotNull
    public Location getPreviousFloor(@NotNull Location location) {
        if (getPreviousFloorNumber(location) == -1) {
            throw new RuntimeException("No more floors!");
        }

        @NotNull Location previousFloorBlock = LOCATIONS.get(getPreviousFloorNumber(location) - 1);
        return new Location(previousFloorBlock.getWorld(),
                previousFloorBlock.getBlockX(),
                previousFloorBlock.getBlockY() + 1,
                previousFloorBlock.getBlockZ());
    }

    public int getCurrentFloorNumber(@NotNull Location location) {
        Objects.requireNonNull(location);

        @NotNull Location cleanedLocation = LocationUtility.cleanedLocation(location);

        if (!IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(cleanedLocation).getBlock().getType())) {
            throw new RuntimeException("Not on a legal elevator block!");
        }

        @NotNull Location elevatorBlockLocation = new Location(location.getWorld(),
                location.getBlockX(),
                location.getBlockY() - 1,
                location.getBlockZ());

        if (LOCATIONS.indexOf(elevatorBlockLocation) != 0) {
            return LOCATIONS.indexOf(elevatorBlockLocation) + 1;
        }

        return -1;
    }

    @NotNull
    public Location getCurrentFloor(@NotNull Location location) {
        if (getCurrentFloorNumber(location) == -1) {
            throw new RuntimeException("No more floors!");
        }

        @NotNull Location previousFloorBlock = LOCATIONS.get(getCurrentFloorNumber(location) - 1);
        return new Location(previousFloorBlock.getWorld(),
                previousFloorBlock.getBlockX(),
                previousFloorBlock.getBlockY() + 1,
                previousFloorBlock.getBlockZ());
    }

    public int getFloorCount() {
        return LOCATIONS.size();
    }
}
