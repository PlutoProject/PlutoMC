package club.plutomc.plutoproject.framework.bukkit.gui.impl.items;

import club.plutomc.plutoproject.framework.bukkit.gui.items.ClickerBuilder;
import club.plutomc.plutoproject.framework.bukkit.gui.items.ImageBuilder;
import club.plutomc.plutoproject.framework.bukkit.gui.items.SlotBuilder;
import club.plutomc.plutoproject.framework.bukkit.gui.Renderer;
import org.jetbrains.annotations.NotNull;

public class SlotBuilderImpl implements SlotBuilder {

    private final Renderer renderer;
    private ClickerBuilderImpl clickerBuilder = null;
    private ImageBuilderImpl imageBuilder = null;

    public SlotBuilderImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public ClickerBuilderImpl getClickerBuilder() {
        return clickerBuilder;
    }

    public ImageBuilderImpl getImageBuilder() {
        return imageBuilder;
    }

    @Override
    public ClickerBuilder clicker() {
        if (this.clickerBuilder == null)
            this.clickerBuilder = new ClickerBuilderImpl(this);
        return this.clickerBuilder;
    }

    @Override
    public ImageBuilder image() {
        if (this.imageBuilder == null)
            this.imageBuilder = new ImageBuilderImpl(this);
        return this.imageBuilder;
    }

    @Override
    public @NotNull Renderer done() {
        return this.renderer;
    }

}
