package ltd.kumo.plutomc.framework.bukkit.gui.pageable;

import ltd.kumo.plutomc.framework.bukkit.gui.Audience;
import ltd.kumo.plutomc.framework.bukkit.gui.Menu;

import java.util.List;

public interface PageableMenu<T> extends Menu {

    /**
     * List all the audiences who are using this menu
     *
     * @return all the audiences
     */
    List<? extends Audience> audiences();

    /**
     * Get currently viewing scene of the audience, null if the audience is not viewing this menu
     *
     * @param audience audience
     * @return current scene
     */
    PageableScene<T> scene(Audience audience);

    /**
     * Open the menu to the audience
     *
     * @param audience new viewer
     * @return renderer
     */
    default PageableRenderer<T> add(Audience audience) {
        return this.add(null, audience);
    }

    /**
     * Open the menu to the audience, will add a back button if previous menu is not null
     * @param previousMenu previous menu
     * @param audience new viewer
     * @return renderer
     */
    PageableRenderer<T> add(Menu previousMenu, Audience audience);

    /**
     * Close if the audience is viewing this menu
     *
     * @param audience viewer
     */
    void remove(Audience audience);

    /**
     * Drop this menu, which means that this menu is not usable
     */
    void drop();

    /**
     * If the menu is available
     *
     * @return the unavailability
     */
    boolean isDropped();

}
