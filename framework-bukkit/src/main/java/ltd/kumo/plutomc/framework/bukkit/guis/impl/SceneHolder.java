package ltd.kumo.plutomc.framework.bukkit.guis.impl;

import lombok.Getter;
import lombok.Setter;
import ltd.kumo.plutomc.framework.bukkit.guis.Audience;
import ltd.kumo.plutomc.framework.bukkit.guis.Menu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class SceneHolder implements InventoryHolder {

    @Getter
    private final Audience audience;
    @Getter
    private final Menu menu;
    @Getter
    @Setter
    private SceneImpl scene;

    private Inventory inventory;

    @Getter
    @Setter
    private boolean save = false;

    public SceneHolder(Audience audience, Menu menu, SceneImpl scene) {
        this.audience = audience;
        this.menu = menu;
        this.scene = scene;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

}
