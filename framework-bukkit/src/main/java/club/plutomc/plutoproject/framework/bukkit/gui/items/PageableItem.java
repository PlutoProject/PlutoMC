package club.plutomc.plutoproject.framework.bukkit.gui.items;

import club.plutomc.plutoproject.framework.bukkit.gui.pageable.PageableScene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PageableItem<T> {

    @NotNull
    ItemStack render(int currentPage, int currentSlot, T item, PageableScene<T> scene);

}
