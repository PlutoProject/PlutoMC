package club.plutomc.plutoproject.framework.bukkit.gui.items;

import club.plutomc.plutoproject.framework.bukkit.gui.pageable.PageableScene;

@FunctionalInterface
public interface PageableClicker<T> {

    void click(int currentPage, int currentSlot, T item, PageableScene<T> scene);

}
