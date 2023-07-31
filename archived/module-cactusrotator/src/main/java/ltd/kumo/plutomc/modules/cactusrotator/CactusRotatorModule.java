package ltd.kumo.plutomc.modules.cactusrotator;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.modules.BukkitModule;
import ltd.kumo.plutomc.modules.cactusrotator.listeners.PlayerListeners;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class CactusRotatorModule extends BukkitModule {

    @NotNull
    private final static ImmutableList<Listener> LISTENERS = ImmutableList.of(
            new PlayerListeners()
    );

    public CactusRotatorModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
    }

    @Override
    public @NotNull String name() {
        return "CactusRotator";
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
