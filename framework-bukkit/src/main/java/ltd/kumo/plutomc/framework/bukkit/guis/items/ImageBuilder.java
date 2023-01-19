package ltd.kumo.plutomc.framework.bukkit.guis.items;

import net.deechael.dutil.builder.BaseBuilder;
import org.jetbrains.annotations.NotNull;

public interface ImageBuilder extends BaseBuilder<Image, SlotBuilder> {

    /**
     * Rendering an image for player
     *
     * @param image image renderer
     * @return self
     */
    ImageBuilder render(Image image);

    /**
     * Finish building clicker and continue build slot
     *
     * @return slot builder
     */
    @NotNull
    SlotBuilder done();

}
