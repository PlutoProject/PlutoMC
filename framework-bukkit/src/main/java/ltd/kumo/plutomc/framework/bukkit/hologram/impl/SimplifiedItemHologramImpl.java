package ltd.kumo.plutomc.framework.bukkit.hologram.impl;

import com.google.common.base.Preconditions;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.hologram.SimplifiedItemHologram;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SimplifiedItemHologramImpl extends ItemHologramImpl implements SimplifiedItemHologram {

    private ItemStack item = new ItemStack(Material.AIR);

    public SimplifiedItemHologramImpl(BukkitPlatform platform, Location location) {
        super(platform, location);
    }

    @Override
    public @NotNull ItemStack item() {
        return this.item;
    }

    @Override
    public void item(@NotNull ItemStack text) {
        Preconditions.checkNotNull(text);
        this.item = text;
        super.setItem((player) -> this.item);
    }

    @NotNull
    @Override
    public Function<BukkitPlayer, ItemStack> getItem() {
        return (player) -> this.item;
    }

    @Override
    public void setItem(@NotNull Function<BukkitPlayer, ItemStack> text) {
        // Ignore
    }

}
