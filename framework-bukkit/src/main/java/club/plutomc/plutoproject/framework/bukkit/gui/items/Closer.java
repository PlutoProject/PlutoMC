package club.plutomc.plutoproject.framework.bukkit.gui.items;

import club.plutomc.plutoproject.framework.bukkit.gui.Scene;

@FunctionalInterface
public interface Closer {

    void close(Scene scene);

}
