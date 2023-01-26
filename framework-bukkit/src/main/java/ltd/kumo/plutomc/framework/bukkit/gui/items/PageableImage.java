package ltd.kumo.plutomc.framework.bukkit.gui.items;

import ltd.kumo.plutomc.framework.bukkit.gui.pageable.PageableScene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PageableImage<T> {

    @NotNull
    ItemStack render(int currentPage, int currentSlot, PageableScene<T> scene);

}
