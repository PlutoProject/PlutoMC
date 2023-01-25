package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * 一种物品显示，放一个假的物品展示框，并在上面放一个假物品，并使物品展示框不可见
 */
public interface WallpaperHologram extends Hologram {

    @Nullable
    Function<BukkitPlayer, ItemStack> getItem();

    void setItem(@NotNull Function<BukkitPlayer, ItemStack> item);

}
