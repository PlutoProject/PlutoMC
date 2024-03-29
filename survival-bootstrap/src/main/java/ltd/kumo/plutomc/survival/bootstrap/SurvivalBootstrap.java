package ltd.kumo.plutomc.survival.bootstrap;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.modules.cactusrotator.CactusRotatorModule;
import ltd.kumo.plutomc.modules.economy.EconomyModule;
import ltd.kumo.plutomc.modules.ironelevator.IronElevatorModule;
import ltd.kumo.plutomc.modules.voidtotem.VoidTotemModule;
import ltd.kumo.plutomc.modules.waxednotwaxed.WaxedNotWaxedModule;
import net.deechael.dutil.Preconditions;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings({"unused"})
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
    public void onEnable() {
        Preconditions.checkNull(bukkitPlatform);
        bukkitPlatform.enable();
        bukkitPlatform.modules(ImmutableList.of(
                new IronElevatorModule(bukkitPlatform),
                new CactusRotatorModule(bukkitPlatform),
                new VoidTotemModule(bukkitPlatform),
                new WaxedNotWaxedModule(bukkitPlatform),
                new EconomyModule(bukkitPlatform)
        ));

        // Hologram test
        /*
        BukkitCommand command = bukkitPlatform.createCommand("hologram");
        command.then("text").executesPlayer((sender, context) -> {
            try {
                TextHologram hologram = bukkitPlatform.getService(HologramService.class).createHologram(TextHologram.class, sender.player().getLocation());
                hologram.setText(player -> Component.text("Fuck you!").color(NamedTextColor.GOLD));
                hologram.addViewer(sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        command.then("item").executesPlayer((sender, context) -> {
            ItemHologram hologram = bukkitPlatform.getService(HologramService.class).createHologram(ItemHologram.class, sender.player().getLocation());
            hologram.setItem(player -> new ItemStack(Material.DIAMOND));
            hologram.addViewer(sender);
        });
        command.then("both").executesPlayer((sender, context) -> {
            HologramGroup group = bukkitPlatform.getService(HologramService.class).createGroup(sender.player().getLocation());
            TextHologram hologram = bukkitPlatform.getService(HologramService.class).createHologram(TextHologram.class, sender.player().getLocation());
            hologram.setText(player -> Component.text("Fuck you!").color(NamedTextColor.GOLD));
            ItemHologram itemHologram = bukkitPlatform.getService(HologramService.class).createHologram(ItemHologram.class, sender.player().getLocation());
            itemHologram.setItem(player -> new ItemStack(Material.DIAMOND));
            group.addHologram(hologram);
            group.addHologram(itemHologram);
            group.addViewer(sender);
        });
        bukkitPlatform.registerCommand("pluto", command);
        */

        // Test for Protocol Injector
        /*
        BukkitCommand command = bukkitPlatform.createCommand("injector");
        command.executes(((sender, context) -> {
            sender.send("Hello world!");
        }));
        bukkitPlatform.registerCommand("pluto", command);
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void test(AsyncMessageSendEvent event) {
                event.setContent(Component.text("Successfully"));
            }
        }, this);
        */

        // Test code, don't delete! Test is still in need!
        // Command test
        /*
        BukkitCommand command = bukkitPlatform.createCommand("test");
        command.executes((bukkitCommandSender, context) -> {
                    bukkitCommandSender.send("Hello world!");
                })
                .thenInteger("age", 1, 18)
                .suggests(suggestion -> suggestion.suggests(1).suggests(18))
                .executes((bukkitCommandSender, context) -> {
                    bukkitCommandSender.send("Your age is " + context.argument(ArgumentInteger.class, "age"));
                })
                .then("message", ArgumentMessage.class)
                .executes((sender, context) -> {
                    sender.send("You send: " + context.argument(ArgumentMessage.class, "message"));
                });
        command.then("first")
                .then("player", ArgumentBukkitPlayer.class)
                .executes((bukkitCommandSender, commandContext) -> {
                    commandContext.argument(ArgumentBukkitPlayer.class, "player")
                            .send("Hello, world!");
                });
        command.then("second")
                .then("players", ArgumentBukkitPlayers.class)
                .executes((bukkitCommandSender, commandContext) -> {
                    commandContext.argument(ArgumentBukkitPlayers.class, "players")
                            .forEach(bukkitPlayer -> bukkitPlayer.send("Multi"));
                });
        command.then("setblock")
                .then("world", ArgumentBukkitWorld.class)
                .then("location", ArgumentBukkitVector.class)
                .executes((sender, context) -> {
                    World world = context.argument(ArgumentBukkitWorld.class, "world");
                    Vector vector = context.argument(ArgumentBukkitVector.class, "location");
                    vector.toLocation(world).getBlock().setType(Material.DIAMOND_BLOCK);
                });
        bukkitPlatform.registerCommand("pluto", command);
        */

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

        // BukkitEconomyService economyService = (BukkitEconomyService) bukkitPlatform.getService(EconomyService.class);

        Objects.requireNonNull(bukkitPlatform).enableModules();
    }

    @Override
    public void onDisable() {
        bukkitPlatform.disable();
        Objects.requireNonNull(bukkitPlatform).disableModules();
    }

}
