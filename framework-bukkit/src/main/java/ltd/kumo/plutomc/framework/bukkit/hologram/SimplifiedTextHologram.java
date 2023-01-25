package ltd.kumo.plutomc.framework.bukkit.hologram;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SimplifiedTextHologram extends TextHologram {

    @Nullable
    Component text();

    void text(@NotNull Component text);

}
