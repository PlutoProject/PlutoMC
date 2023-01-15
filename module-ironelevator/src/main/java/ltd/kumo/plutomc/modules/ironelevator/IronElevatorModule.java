package ltd.kumo.plutomc.modules.ironelevator;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.modules.BukkitModule;
import ltd.kumo.plutomc.modules.ironelevator.listeners.PlayerListeners;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class IronElevatorModule extends BukkitModule {

    @NotNull
    private final static ImmutableList<Listener> LISTENERS = ImmutableList.of(
            new PlayerListeners()
    );

    public IronElevatorModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
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
