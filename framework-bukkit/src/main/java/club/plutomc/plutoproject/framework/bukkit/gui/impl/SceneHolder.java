package club.plutomc.plutoproject.framework.bukkit.gui.impl;

import lombok.Getter;
import lombok.Setter;
import club.plutomc.plutoproject.framework.bukkit.gui.Audience;
import club.plutomc.plutoproject.framework.bukkit.gui.Menu;
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

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
