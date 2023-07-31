package club.plutomc.plutoproject.framework.bukkit.gui.items;

import club.plutomc.plutoproject.framework.bukkit.gui.Scene;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface Clicker {

    void click(int currentSlot, Scene scene, ItemStack cursor);

}
