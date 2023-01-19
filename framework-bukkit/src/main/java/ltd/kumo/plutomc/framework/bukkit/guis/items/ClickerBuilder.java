package ltd.kumo.plutomc.framework.bukkit.guis.items;

import net.deechael.dutil.builder.BaseBuilder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public interface ClickerBuilder extends BaseBuilder<Clicker, SlotBuilder> {

    /**
     * Set not allowed actions
     *
     * @param notAllowed white list
     * @return self
     */
    ClickerBuilder actions(InventoryAction... notAllowed);

    /**
     * Set allowed click types
     *
     * @param allowed white list
     * @return self
     */
    ClickerBuilder clicks(ClickType... allowed);

    /**
     * Execute a clicking
     *
     * @param clicker clicker
     * @return self
     */
    ClickerBuilder executes(Clicker clicker);

    /**
     * Finish building clicker and continue build slot
     *
     * @return slot builder
     */
    @NotNull
    SlotBuilder done();

}
