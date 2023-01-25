package ltd.kumo.plutomc.framework.bukkit.hologram;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface SimplifiedTextHologram extends TextHologram {

    @NotNull
    Component text();

    void text(@NotNull Component text);

}
