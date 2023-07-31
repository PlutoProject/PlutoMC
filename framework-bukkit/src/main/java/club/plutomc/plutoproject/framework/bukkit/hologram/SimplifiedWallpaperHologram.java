package club.plutomc.plutoproject.framework.bukkit.hologram;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface SimplifiedWallpaperHologram extends WallpaperHologram {

    @NotNull
    ItemStack item();

    void item(ItemStack item);

}
