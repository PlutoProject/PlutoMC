package club.plutomc.plutoproject.framework.bukkit.gui.pageable;

import club.plutomc.plutoproject.framework.bukkit.gui.Renderer;
import club.plutomc.plutoproject.framework.bukkit.gui.Scene;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface PageableScene<T> extends Scene {

    @Override
    default @NotNull Renderer refresh(boolean keepLast) {
        throw new RuntimeException("Pageable Scene cannot be refreshed with normal Renderer");
    }

    @Override
    default @NotNull ItemStack item(int raw) {
        throw new RuntimeException("Pageable Scene cannot get item");
    }

}
