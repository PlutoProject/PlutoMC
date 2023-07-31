package club.plutomc.plutoproject.framework.bukkit.gui.items;

import club.plutomc.plutoproject.framework.bukkit.gui.Scene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Image {

    @Nullable
    ItemStack render(int currentSlot, Scene scene);

}
