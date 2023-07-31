package club.plutomc.plutoproject.framework.bukkit.hologram;

import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * 一种物品显示，以掉落物的形式展示物品
 */
public interface ItemHologram extends Hologram {

    @NotNull
    Function<BukkitPlayer, ItemStack> getItem();

    void setItem(@NotNull Function<BukkitPlayer, ItemStack> item);

}
