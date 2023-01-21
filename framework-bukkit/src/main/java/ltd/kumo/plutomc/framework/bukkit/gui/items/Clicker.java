package ltd.kumo.plutomc.framework.bukkit.gui.items;

import ltd.kumo.plutomc.framework.bukkit.gui.Scene;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface Clicker {

    void click(int currentSlot, Scene scene, ItemStack cursor);

}
