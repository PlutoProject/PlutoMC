package ltd.kumo.plutomc.framework.bukkit.gui.impl.items;

import ltd.kumo.plutomc.framework.bukkit.gui.items.Image;
import ltd.kumo.plutomc.framework.bukkit.gui.items.ImageBuilder;
import ltd.kumo.plutomc.framework.bukkit.gui.items.SlotBuilder;
import org.jetbrains.annotations.NotNull;

public class ImageBuilderImpl implements ImageBuilder {

    private final SlotBuilder slotBuilder;
    private Image image;

    public ImageBuilderImpl(SlotBuilder slotBuilder) {
        this.slotBuilder = slotBuilder;
    }

    @Override
    public ImageBuilder render(Image image) {
        this.image = image;
        return this;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public @NotNull SlotBuilder done() {
        return this.slotBuilder;
    }

    @Override
    public @NotNull Image build() {
        return null;
    }
}
