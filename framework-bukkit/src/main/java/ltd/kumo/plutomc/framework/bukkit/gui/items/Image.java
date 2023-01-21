package ltd.kumo.plutomc.framework.bukkit.gui.items;

import ltd.kumo.plutomc.framework.bukkit.gui.Scene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Image {

    @Nullable
    ItemStack render(int currentSlot, Scene scene);

}
