package ltd.kumo.plutomc.framework.bukkit.guis.impl;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.guis.Audience;
import ltd.kumo.plutomc.framework.bukkit.guis.Menu;
import ltd.kumo.plutomc.framework.bukkit.guis.Renderer;
import ltd.kumo.plutomc.framework.bukkit.guis.Scene;
import ltd.kumo.plutomc.framework.bukkit.guis.impl.items.SlotBuilderImpl;
import ltd.kumo.plutomc.framework.bukkit.guis.items.Closer;
import ltd.kumo.plutomc.framework.bukkit.guis.items.SlotBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.Map;

public class RendererImpl implements Renderer {

    private final SceneImpl previousScene;

    private final BukkitPlatform platform;
    private final Audience audience;
    private final Menu menu;
    private Component title;
    private Closer closer;
    private int lines = 6;
    private final Map<Integer, SlotBuilderImpl> slotBuilders = new HashMap<>();

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
