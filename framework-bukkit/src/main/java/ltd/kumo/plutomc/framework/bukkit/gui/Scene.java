package ltd.kumo.plutomc.framework.bukkit.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Scene {

    /**
     * Get the menu of the scene
     *
     * @return the menu
     */
    @NotNull
    Menu menu();

    /**
     * Get the viewer of the scene
     *
     * @return the scene
     */
    @NotNull
    Audience viewer();

    /**
     * Render this menu for this player
     *
     * @param keepLast if renderer should keep the slots from last inventory
     * @return renderer
     */
    @NotNull
    Renderer refresh(boolean keepLast);

    /**
     * Get bukkit inventory object
     *
     * @return inventory
     */
    @NotNull
    Inventory asBukkit();

    /**
     * Get the item stack at the slot
     *
     * @param raw slot index
     * @return item stack
     */
    @NotNull
    ItemStack item(int raw);

}
