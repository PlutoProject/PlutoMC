package ltd.kumo.plutomc.framework.bukkit.guis;

import java.util.List;

public interface Menu {

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
    Scene scene(Audience audience);

    /**
     * Open the menu to the audience
     *
     * @param audience new viewer
     * @return renderer
     */
    Renderer add(Audience audience);

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
