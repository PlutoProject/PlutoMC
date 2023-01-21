package ltd.kumo.plutomc.framework.bukkit.gui;

import ltd.kumo.plutomc.framework.bukkit.gui.items.Closer;
import ltd.kumo.plutomc.framework.bukkit.gui.items.SlotBuilder;
import net.kyori.adventure.text.Component;

public interface Renderer {

    /**
     * The menu owning this renderer
     *
     * @return owner
     */
    Menu menu();

    /**
     * Who is viewing this menu
     *
     * @return viewer
     */
    Audience viewer();

    /**
     * Set the title of the menu
     *
     * @param title new title
     * @return self
     */
    Renderer title(String title);

    /**
     * Set the title of the menu
     *
     * @param title new title
     * @return self
     */
    Renderer title(Component title);

    /**
     * Set the lines of the menu
     *
     * @param lines lines in [1, 6]
     * @return renderer
     */
    Renderer line(int lines);

    /**
     * Set the event handler of closing
     *
     * @param closer event handler
     * @return self
     */
    Renderer close(Closer closer);

    /**
     * Start building a slot
     *
     * @param raw slot index
     * @return slot builder
     */
    SlotBuilder slot(int raw);

    /**
     * Render this menu
     *
     * @return rendered scene
     */
    Scene render();

}
