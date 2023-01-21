package ltd.kumo.plutomc.framework.bukkit.gui.items;

import ltd.kumo.plutomc.framework.bukkit.gui.Scene;

@FunctionalInterface
public interface Closer {

    void close(Scene scene);

}
