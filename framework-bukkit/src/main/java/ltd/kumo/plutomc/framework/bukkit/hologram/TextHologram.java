package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface TextHologram extends Hologram {

    @NotNull
    Function<BukkitPlayer, Component> getText();

    void setText(@NotNull Function<BukkitPlayer, Component> text);

}
