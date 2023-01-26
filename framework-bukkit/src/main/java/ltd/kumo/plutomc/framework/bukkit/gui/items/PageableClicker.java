package ltd.kumo.plutomc.framework.bukkit.gui.items;

import ltd.kumo.plutomc.framework.bukkit.gui.pageable.PageableScene;

@FunctionalInterface
public interface PageableClicker<T> {

    void click(int currentPage, int currentSlot, T item, PageableScene<T> scene);

}
