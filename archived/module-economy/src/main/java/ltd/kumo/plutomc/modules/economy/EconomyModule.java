package ltd.kumo.plutomc.modules.economy;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.modules.BukkitModule;
import ltd.kumo.plutomc.modules.economy.commands.EconomyCommand;
import ltd.kumo.plutomc.modules.economy.commands.PayCommand;
import org.jetbrains.annotations.NotNull;

public class EconomyModule extends BukkitModule {

    public EconomyModule(BukkitPlatform bukkitPlatform) {
        super(bukkitPlatform);
    }

    @Override
    public @NotNull String name() {
        return "Economy";
    }

    @Override
    public boolean shouldBeEnabled() {
        return true;
    }

    @Override
    public void initial() {
        this.platform.registerCommand("pluto", EconomyCommand.command(this.platform()));
        this.platform.registerCommand("pluto", PayCommand.command(this.platform()));
    }

    @Override
    public void terminate() {

    }

    @Override
    public void reload() {

    }

}
