package ltd.kumo.plutomc.framework.bukkit.hologram;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SimplifiedBlockHologram extends BlockHologram {

    @Nullable
    ItemStack item();

    void item(ItemStack item);

}
