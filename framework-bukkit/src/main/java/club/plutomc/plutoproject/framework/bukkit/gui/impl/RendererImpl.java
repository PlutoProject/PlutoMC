package club.plutomc.plutoproject.framework.bukkit.gui.impl;

import club.plutomc.plutoproject.framework.bukkit.gui.impl.items.SlotBuilderImpl;
import club.plutomc.plutoproject.framework.bukkit.gui.items.Closer;
import club.plutomc.plutoproject.framework.bukkit.gui.items.SlotBuilder;
import club.plutomc.plutoproject.framework.bukkit.BukkitPlatform;
import club.plutomc.plutoproject.framework.bukkit.gui.Audience;
import club.plutomc.plutoproject.framework.bukkit.gui.Menu;
import club.plutomc.plutoproject.framework.bukkit.gui.Renderer;
import club.plutomc.plutoproject.framework.bukkit.gui.Scene;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.Map;

public class RendererImpl implements Renderer {

    private final SceneImpl previousScene;

    private final BukkitPlatform platform;
    private final Audience audience;
    private final Menu menu;
    private final Map<Integer, SlotBuilderImpl> slotBuilders = new HashMap<>();
    private Component title;
    private Closer closer;
    private int lines = 6;

    public RendererImpl(SceneImpl previousScene, BukkitPlatform platform, Menu menu, Audience audience) {
        this.previousScene = previousScene;
        this.platform = platform;
        this.menu = menu;
        this.audience = audience;
    }

    public SceneImpl getPreviousScene() {
        return previousScene;
    }

    @Override
    public Menu menu() {
        return this.menu;
    }

    @Override
    public Audience viewer() {
        return this.audience;
    }

    @Override
    public Renderer title(String title) {
        this.title = MiniMessage.miniMessage().deserialize(title);
        return this;
    }

    @Override
    public Renderer title(Component title) {
        this.title = title;
        return this;
    }

    @Override
    public Renderer line(int lines) {
        if (lines > 6)
            throw new ArrayIndexOutOfBoundsException("Lines must in [1, 6]");
        if (lines < 1)
            throw new ArrayIndexOutOfBoundsException("Lines must in [1, 6]");
        this.lines = lines;
        return this;
    }

    @Override
    public Renderer close(Closer closer) {
        this.closer = closer;
        return this;
    }

    @Override
    public SlotBuilder slot(int raw) {
        if (!this.slotBuilders.containsKey(raw))
            this.slotBuilders.put(raw, new SlotBuilderImpl(this));
        return this.slotBuilders.get(raw);
    }

    @Override
    public Scene render() {
        return new SceneImpl(this.previousScene,
                SceneImpl.PreviousSettings.Builder.of()
                        .title(this.title)
                        .closer(this.closer)
                        .lines(this.lines)
                        .slots(this.slotBuilders)
                        .build(),
                this.platform,
                this.menu,
                this.audience);
    }

}
