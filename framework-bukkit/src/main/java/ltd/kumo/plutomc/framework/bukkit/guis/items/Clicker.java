package ltd.kumo.plutomc.framework.bukkit.guis.items;

import ltd.kumo.plutomc.framework.bukkit.guis.Scene;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface Clicker {

    void click(int currentSlot, Scene scene, ItemStack cursor);

}
