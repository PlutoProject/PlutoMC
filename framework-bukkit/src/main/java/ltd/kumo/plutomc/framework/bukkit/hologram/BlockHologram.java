package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;


/**
 * 一种物品显示，不过是直接放置在盔甲架的头上，静态，所以称之为方块全息字
 */
public interface BlockHologram extends Hologram {

    @NotNull
    Function<BukkitPlayer, ItemStack> getItem();

    void setItem(@NotNull Function<BukkitPlayer, ItemStack> item);


}
