package ltd.kumo.plutomc.framework.bukkit.gui.impl.items;

import ltd.kumo.plutomc.framework.bukkit.gui.items.Clicker;
import ltd.kumo.plutomc.framework.bukkit.gui.items.ClickerBuilder;
import ltd.kumo.plutomc.framework.bukkit.gui.items.SlotBuilder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickerBuilderImpl implements ClickerBuilder {

    private final SlotBuilder slotBuilder;

    private final List<InventoryAction> actions = new ArrayList<>();
    private final List<ClickType> clickTypes = new ArrayList<>();
    private Clicker clicker;

    public ClickerBuilderImpl(SlotBuilder slotBuilder) {
        this.slotBuilder = slotBuilder;
    }

    @Override
    public ClickerBuilder actions(InventoryAction... allowed) {
        this.actions.addAll(Arrays.asList(allowed));
        return this;
    }

    public List<InventoryAction> getActions() {
        return new ArrayList<>(this.actions);
    }

    @Override
    public ClickerBuilder clicks(ClickType... allowed) {
        this.clickTypes.addAll(Arrays.asList(allowed));
        return this;
    }

    public List<ClickType> getClickTypes() {
        return new ArrayList<>(this.clickTypes);
    }

    @Override
    public ClickerBuilder executes(Clicker clicker) {
        this.clicker = clicker;
        return this;
    }

    public Clicker getClicker() {
        return clicker;
    }

    @Override
    public @NotNull SlotBuilder done() {
        return this.slotBuilder;
    }

    @Override
    public @NotNull Clicker build() {
        return null;
    }

}
