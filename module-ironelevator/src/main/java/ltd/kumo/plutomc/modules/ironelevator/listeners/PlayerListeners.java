package ltd.kumo.plutomc.modules.ironelevator.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorChain;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import ltd.kumo.plutomc.modules.ironelevator.utilites.LocationUtility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class PlayerListeners implements Listener {
    @EventHandler
    public void playerJumpEvent(PlayerJumpEvent event) {
        @NotNull Player player = event.getPlayer();
        @NotNull Location location = LocationUtility.cleanedLocation(player.getLocation());

        if (IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(location).getBlock().getType())) {
            CompletableFuture.supplyAsync(() -> {
                @NotNull IronElevatorChain ironElevatorChain = IronElevatorChain.from(location);
                return ironElevatorChain;
            }).thenAccept(ironElevatorChain -> {
                if (ironElevatorChain.getNextFloorNumber(location) != -1) {
                    @NotNull Location target = player.getLocation();
                    target.setY(ironElevatorChain.getNextFloor(location).getBlockY());

                    IronElevatorModule.prompt(player, ironElevatorChain.getNextFloorNumber(location), ironElevatorChain.getFloorCount());
                    player.teleportAsync(target);

                }
            });
        }
    }

    @EventHandler
    public void playerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            @NotNull Player player = event.getPlayer();
            @NotNull Location location = LocationUtility.cleanedLocation(player.getLocation());

            if (IronElevatorModule.ELEVATOR_MATERIALS.contains(LocationUtility.getUnder(location).getBlock().getType())) {
                CompletableFuture.supplyAsync(() -> {
                    @NotNull IronElevatorChain ironElevatorChain = IronElevatorChain.from(location);
                    return ironElevatorChain;
                }).thenAccept(ironElevatorChain -> {
                    if (ironElevatorChain.getPreviousFloorNumber(location) != -1) {
                        @NotNull Location target = player.getLocation();
                        target.setY(ironElevatorChain.getPreviousFloor(location).getBlockY());

                        IronElevatorModule.prompt(player, ironElevatorChain.getPreviousFloorNumber(location), ironElevatorChain.getFloorCount());
                        player.teleportAsync(target);
                    }
                });
            }
        }
    }
}
