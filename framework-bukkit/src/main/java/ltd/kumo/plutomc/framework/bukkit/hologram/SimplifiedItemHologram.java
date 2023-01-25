package ltd.kumo.plutomc.framework.bukkit.hologram;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface SimplifiedItemHologram extends ItemHologram {

    @NotNull
    ItemStack item();

    void item(ItemStack item);

}
