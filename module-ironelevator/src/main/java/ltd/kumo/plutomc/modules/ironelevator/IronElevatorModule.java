package ltd.kumo.plutomc.modules.ironelevator;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.modules.BukkitModule;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import ltd.kumo.plutomc.modules.ironelevator.listeners.PlayerListeners;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class IronElevatorModule extends BukkitModule {

    @NotNull
    public final static ImmutableList<Material> ELEVATOR_MATERIALS = ImmutableList.of(
            Material.COPPER_BLOCK,
            Material.WAXED_COPPER_BLOCK
    );

    @NotNull
    private final static ImmutableList<Listener> LISTENERS = ImmutableList.of(
            new PlayerListeners()
    );

    public IronElevatorModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
    }

    public static void prompt(@NotNull Player player, int current, int max) {
        player.showTitle(Title.title(
                Component.text(" "),
                Component.text(current + " / ").append(
                        Component.text(max + " 层")
                ).color(Catppuccin.MOCHA.TEAL)
        ));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
    }

    @Override
    @NotNull
    public String name() {
        return "IronElevator";
    }

    @Override
    public boolean shouldBeEnabled() {
        return true;
    }

    @Override
    public void initial() {
        LISTENERS.forEach(this::listener);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void reload() {

    }
}
