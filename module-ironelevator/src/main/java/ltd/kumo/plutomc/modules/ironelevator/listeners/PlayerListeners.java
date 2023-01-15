package ltd.kumo.plutomc.modules.ironelevator.listeners;

import ltd.kumo.plutomc.modules.ironelevator.IronElevatorChain;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import ltd.kumo.plutomc.modules.ironelevator.utilites.LocationUtility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class PlayerListeners implements Listener {
    @EventHandler
    public void playerJumpEvent(PlayerJoinEvent event) {
        @NotNull Player player = event.getPlayer();
        @NotNull Location location = LocationUtility.cleanedLocation(player.getLocation());

        if (IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(location).getBlock().getType())) {
            CompletableFuture.supplyAsync(() -> {
                @NotNull IronElevatorChain ironElevatorChain = IronElevatorChain.from(location);
                return ironElevatorChain;
            }).thenAccept(ironElevatorChain -> {
                if (ironElevatorChain.getNextFloorNumber(location) != -1) {
                    player.teleportAsync(ironElevatorChain.getNextFloor(location));
                    IronElevatorModule.prompt(player, ironElevatorChain.getCurrentFloorNumber(location), ironElevatorChain.getFloorCount());
                }
            });
        }
    }

    @EventHandler
    public void playerToggleSprintEvent(PlayerToggleSprintEvent event) {
        @NotNull Player player = event.getPlayer();
        @NotNull Location location = LocationUtility.cleanedLocation(player.getLocation());

        if (IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(location).getBlock().getType())) {
            CompletableFuture.supplyAsync(() -> {
                @NotNull IronElevatorChain ironElevatorChain = IronElevatorChain.from(location);
                return ironElevatorChain;
            }).thenAccept(ironElevatorChain -> {
                if (ironElevatorChain.getPreviousFloorNumber(location) != -1) {
                    player.teleportAsync(ironElevatorChain.getPreviousFloor(location));
                    IronElevatorModule.prompt(player, ironElevatorChain.getCurrentFloorNumber(location), ironElevatorChain.getFloorCount());
                }
            });
        }
    }
}
