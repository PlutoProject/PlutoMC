package ltd.kumo.plutomc.framework.bukkit.guis.impl;

import lombok.Getter;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.guis.Audience;
import ltd.kumo.plutomc.framework.bukkit.guis.Menu;
import ltd.kumo.plutomc.framework.bukkit.guis.Renderer;
import ltd.kumo.plutomc.framework.bukkit.guis.Scene;
import ltd.kumo.plutomc.framework.bukkit.guis.impl.items.ClickerBuilderImpl;
import ltd.kumo.plutomc.framework.bukkit.guis.impl.items.ImageBuilderImpl;
import ltd.kumo.plutomc.framework.bukkit.guis.impl.items.SlotBuilderImpl;
import ltd.kumo.plutomc.framework.bukkit.guis.items.Clicker;
import ltd.kumo.plutomc.framework.bukkit.guis.items.Closer;
import ltd.kumo.plutomc.framework.bukkit.guis.items.Image;
import ltd.kumo.plutomc.framework.bukkit.guis.items.SlotBuilder;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SceneImpl implements Scene {

    @Getter
    private final PreviousSettings previousSettings;
    private final BukkitPlatform platform;
    private final Menu menu;
    private final Audience audience;
    private final Inventory bukkit;
    private final SceneHolder holder;

    public SceneImpl(SceneImpl previousScene, PreviousSettings previousSettings, BukkitPlatform platform, Menu menu, Audience audience) {
        this.previousSettings = previousSettings;
        this.platform = platform;
        this.menu = menu;
        this.audience = audience;
        if (previousScene != null && previousScene.previousSettings.getLines() == this.previousSettings.getLines()) {
            this.holder = previousScene.holder;
            this.holder.setSave(true);
            this.holder.setScene(this);
            this.bukkit = previousScene.asBukkit();
            for (ItemStack itemStack : this.bukkit) {
                this.bukkit.remove(itemStack);
            }
            for (int i = 0; i < (this.previousSettings.getLines() * 9); i++)
                processImage(this.bukkit, i);
        } else {
            this.holder = new SceneHolder(this.audience, this.menu, this);
            this.holder.setSave(true);
            if (previousSettings.title != null)
                this.bukkit = Bukkit.createInventory(holder, this.previousSettings.getLines() * 9, this.previousSettings.getTitle());
            else
                this.bukkit = Bukkit.createInventory(holder, this.previousSettings.getLines() * 9);
            this.holder.setInventory(this.bukkit);
            for (int i = 0; i < (this.previousSettings.getLines() * 9); i++)
                processImage(this.bukkit, i);
            ((BukkitPlayer) audience).player().closeInventory();
            ((BukkitPlayer) audience).player().openInventory(this.bukkit);
        }
        ((MenuImpl) menu).ensurePlayer(audience, this);
    }

    @Override
    public @NotNull Menu menu() {
        return this.menu;
    }

    @Override
    public @NotNull Audience viewer() {
        return this.audience;
    }

    @Override
    public @NotNull Renderer refresh(boolean keepLast) {
        RendererImpl renderer = new RendererImpl(this, this.platform, this.menu, this.audience);
        if (keepLast && this.previousSettings != null) {
            renderer.title(this.previousSettings.getTitle())
                    .close(this.previousSettings.getCloser())
                    .line(this.previousSettings.getLines());
            for (Map.Entry<Integer, SlotBuilderImpl> entry : this.previousSettings.getSlotBuilders().entrySet()) {
                SlotBuilder newBuilder = renderer.slot(entry.getKey());
                ClickerBuilderImpl clickerBuilder = entry.getValue().getClickerBuilder();
                ImageBuilderImpl imageBuilder = entry.getValue().getImageBuilder();
                if (clickerBuilder != null)
                    newBuilder.clicker()
                            .actions(clickerBuilder.getActions().toArray(new InventoryAction[0]))
                            .clicks(clickerBuilder.getClickTypes().toArray(new ClickType[0]))
                            .executes(clickerBuilder.getClicker());
                if (imageBuilder != null)
                    newBuilder.image()
                            .render(imageBuilder.getImage());
            }
        }
        return renderer;
    }

    @Override
    public @NotNull Inventory asBukkit() {
        return this.bukkit;
    }

    @Override
    public @NotNull ItemStack item(int raw) {
        return Objects.requireNonNull(asBukkit().getItem(raw));
    }

    public void processClick(int slot, InventoryAction action, ClickType clickType, ItemStack cursor) {
        SlotBuilderImpl slotBuilder = this.previousSettings.getSlotBuilders().get(slot);
        if (slotBuilder == null)
            return;
        ClickerBuilderImpl clickerBuilder = slotBuilder.getClickerBuilder();
        if (clickerBuilder == null)
            return;
        Clicker clicker = clickerBuilder.getClicker();
        if (clicker == null)
            return;
        if (clickerBuilder.getActions().contains(action))
            return;
        if (!clickerBuilder.getClickTypes().contains(clickType))
            return;
        clicker.click(slot, this, cursor);
    }

    private void processImage(Inventory inventory, int slot) {
        SlotBuilderImpl slotBuilder = this.previousSettings.getSlotBuilders().get(slot);
        if (slotBuilder == null)
            return;
        ImageBuilderImpl imageBuilder = slotBuilder.getImageBuilder();
        if (imageBuilder == null)
            return;
        Image image = imageBuilder.getImage();
        if (image == null)
            return;
        inventory.setItem(slot, image.render(slot, this));
    }

    protected static class PreviousSettings {

        @Getter
        private final Component title;
        @Getter
        private final Closer closer;
        @Getter
        private final Map<Integer, SlotBuilderImpl> slotBuilders;
        @Getter
        private int lines = 6;

        public PreviousSettings(Component title, Closer closer, int lines, Map<Integer, SlotBuilderImpl> slotBuilders) {
            this.title = title;
            this.closer = closer;
            this.lines = lines;
            this.slotBuilders = new HashMap<>(slotBuilders);
        }

        protected static class Builder {

            @Getter
            private Component title;
            @Getter
            private Closer closer;
            @Getter
            private int lines = 6;
            @Getter
            private Map<Integer, SlotBuilderImpl> slotBuilders;

            private Builder() {
            }

            public static Builder of() {
                return new Builder();
            }

            public Builder title(Component component) {
                this.title = component;
                return this;
            }

            public Builder closer(Closer closer) {
                this.closer = closer;
                return this;
            }

            public Builder lines(int lines) {
                this.lines = lines;
                return this;
            }

            public Builder slots(Map<Integer, SlotBuilderImpl> slotBuilders) {
                this.slotBuilders = slotBuilders;
                return this;
            }

            public PreviousSettings build() {
                return new PreviousSettings(this.title, this.closer, this.lines, this.slotBuilders == null ? new HashMap<>() : this.slotBuilders);
            }

        }

    }

}
