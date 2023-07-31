package club.plutomc.plutoproject.framework.bukkit.hologram.impl;

import club.plutomc.plutoproject.framework.bukkit.BukkitPlatform;
import com.google.common.base.Preconditions;
import club.plutomc.plutoproject.framework.bukkit.hologram.SimplifiedTextHologram;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SimplifiedTextHologramImpl extends TextHologramImpl implements SimplifiedTextHologram {

    private Component text = Component.text("Hello, world!");

    public SimplifiedTextHologramImpl(BukkitPlatform platform, Location location) {
        super(platform, location);
    }

    @Override
    public @NotNull Component text() {
        return this.text;
    }

    @Override
    public void text(@NotNull Component text) {
        Preconditions.checkNotNull(text);
        this.text = text;
        super.setText((player) -> this.text);
    }

    @NotNull
    @Override
    public Function<BukkitPlayer, Component> getText() {
        return (player) -> this.text;
    }

    @Override
    public void setText(@NotNull Function<BukkitPlayer, Component> text) {
        // Ignore
    }

}
