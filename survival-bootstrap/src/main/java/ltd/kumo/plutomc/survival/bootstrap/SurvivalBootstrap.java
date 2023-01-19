package ltd.kumo.plutomc.survival.bootstrap;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.modules.cactusrotator.CactusRotatorModule;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import ltd.kumo.plutomc.modules.voidtotem.VoidTotemModule;
import ltd.kumo.plutomc.modules.waxednotwaxed.WaxedNotWaxedModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public class SurvivalBootstrap extends JavaPlugin {

    @Nullable
    private static BukkitPlatform bukkitPlatform;

    public static void reload() {
        Objects.requireNonNull(bukkitPlatform).reloadModules();
    }

    @Nullable
    public static BukkitPlatform bukkitPlatform() {
        return bukkitPlatform;
    }

    @NotNull
    public static SurvivalBootstrap instance() {
        return SurvivalBootstrap.getPlugin(SurvivalBootstrap.class);
    }

    @Override
    public void onLoad() {
        bukkitPlatform = BukkitPlatform.of(this);
        bukkitPlatform.load();
    }

    @Override
    // @SuppressWarnings("unchecked")
    public void onEnable() {
        bukkitPlatform.enable();
        bukkitPlatform.modules(ImmutableList.of(
                new IronElevatorModule(bukkitPlatform),
                new CactusRotatorModule(bukkitPlatform),
                new VoidTotemModule(bukkitPlatform),
                new WaxedNotWaxedModule(bukkitPlatform)
        ));

        // Test code, don't delete! Test is still in need!
        // BukkitCommand command = bukkitPlatform.createCommand("test");
        // command.executes((bukkitCommandSender, context) -> {
        //     bukkitCommandSender.send("Hello world!");
        // })
        //         .thenInteger("age", 1, 18)
        //         .suggests(suggestion -> suggestion.suggests(1))
        //         .executes((bukkitCommandSender, context) -> {
        //             bukkitCommandSender.send("Your age is " + context.argument(ArgumentInteger.class, "age"));
        //         })
        //             .then("message", ArgumentMessage.class)
        //             .executes((sender, context) -> {
        //                 sender.send("You send: " + context.argument(ArgumentMessage.class, "message"));
        //             });
        // bukkitPlatform.registerCommand("pluto", command);

        // GUI test
        /*
        BukkitCommand command = bukkitPlatform.createCommand("test");
        Menu menu = bukkitPlatform.createMenu();
        command.executesPlayer((bukkitPlayer, context) -> {
            menu.add(bukkitPlayer)
                    .title("Hello, world!")
                    .slot(0)
                    .image()
                    .render(((currentSlot, scene) -> {
                        ItemStack itemStack = new ItemStack(Material.DIAMOND);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Click to refresh"));
                        itemStack.setItemMeta(itemMeta);
                        return itemStack;
                    }))
                    .done()
                    .clicker()
                    .clicks(ClickType.LEFT, ClickType.SHIFT_LEFT)
                    .actions(InventoryAction.MOVE_TO_OTHER_INVENTORY)
                    .executes((currentSlot, scene, cursor) -> {
                        ((BukkitPlayer) scene.viewer()).send("hello, world!");
                        scene.refresh(true)
                                .slot(4)
                                .image()
                                .render(((slot1, scene1) -> {
                                    ItemStack itemStack = new ItemStack(Material.EMERALD);
                                    ItemMeta itemMeta = itemStack.getItemMeta();
                                    itemMeta.displayName(Component.text("Click to reduce lines"));
                                    itemStack.setItemMeta(itemMeta);
                                    return itemStack;
                                }))
                                .done()
                                .clicker()
                                .clicks(ClickType.LEFT, ClickType.SHIFT_LEFT)
                                .actions(InventoryAction.MOVE_TO_OTHER_INVENTORY)
                                .executes((slot1, scene1, cursor1) -> {
                                    scene1.refresh(false)
                                            .line(5)
                                            .slot(8)
                                            .image()
                                            .render(((slot2, scene2) -> {
                                                ItemStack itemStack = new ItemStack(Material.PAPER);
                                                ItemMeta itemMeta = itemStack.getItemMeta();
                                                itemMeta.displayName(Component.text("Current slot index: " + slot2));
                                                return itemStack;
                                            }))
                                            .done()
                                            .done()
                                            .render();
                                })
                                .done()
                                .done()
                                .render();
                    })
                    .done()
                    .done()
                    .render();
        });
        bukkitPlatform.registerCommand("pluto", command);
        */

        Objects.requireNonNull(bukkitPlatform).enableModules();
    }

    @Override
    public void onDisable() {
        bukkitPlatform.disable();
        Objects.requireNonNull(bukkitPlatform).disableModules();
    }

}
