package club.plutomc.plutoproject.framework.bukkit;

import club.plutomc.plutoproject.framework.bukkit.economy.BukkitEconomyService;
import club.plutomc.plutoproject.framework.bukkit.gui.Menu;
import club.plutomc.plutoproject.framework.bukkit.gui.impl.MenuImpl;
import com.google.common.collect.ImmutableList;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommand;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandManager;
import club.plutomc.plutoproject.framework.bukkit.hologram.HologramService;
import club.plutomc.plutoproject.framework.bukkit.hologram.impl.BukkitHologramService;
import club.plutomc.plutoproject.framework.bukkit.injector.ProtocolInjector;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import club.plutomc.plutoproject.framework.shared.Platform;
import club.plutomc.plutoproject.framework.shared.Service;
import club.plutomc.plutoproject.framework.shared.command.Command;
import club.plutomc.plutoproject.framework.shared.command.CommandSender;
import club.plutomc.plutoproject.framework.shared.command.executors.PlayerExecutor;
import club.plutomc.plutoproject.framework.shared.economy.EconomyService;
import club.plutomc.plutoproject.framework.shared.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings({"unused", "unchecked"})
public class BukkitPlatform extends Platform<JavaPlugin> implements Listener {

    private final Map<Class<?>, Service<?>> services = new HashMap<>();
    private BukkitCommandManager commandManager;
    private ProtocolInjector injector;

    private BukkitPlatform(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    public @NotNull
    static BukkitPlatform of(@NotNull JavaPlugin plugin) {
        Objects.requireNonNull(plugin);
        return new BukkitPlatform(plugin);
    }

    @Override
    public @NotNull ImmutableList<?> onlinePlayers() {
        return ImmutableList.copyOf(plugin().getServer().getOnlinePlayers());
    }

    @Override
    public @NotNull String name() {
        return "Bukkit";
    }

    @Override
    public @NotNull String version() {
        return plugin().getServer().getVersion();
    }

    @Override
    public BukkitCommand createCommand(String name) {
        return new BukkitCommand(this, name);
    }

    public Menu createMenu() {
        return new MenuImpl(this);
    }

    @Override
    public <E extends CommandSender, P extends Player<?>, X extends PlayerExecutor> void registerCommand(String prefix, Command<E, P, X> command) {
        this.commandManager.register(prefix, (BukkitCommand) command);
    }

    @Override
    public <E extends Service<E>> @NotNull E getService(Class<E> clazz) {
        return Optional.ofNullable((E) this.services.get(clazz)).orElseThrow();
    }

    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public void enableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.initial();
            }
        });
    }

    @Override
    public void disableModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.terminate();
            }
        });
    }

    @Override
    public void reloadModules() {
        modules().forEach(module -> {
            if (module.shouldBeEnabled()) {
                module.reload();
            }
        });
    }

    @Override
    public void load() {
    }

    @Override
    public void enable() {
        this.injector = new ProtocolInjector(this);

        this.commandManager = new BukkitCommandManager(this);

        this.services.put(ProtocolInjector.class, this.injector);

        BukkitEconomyService economyService = new BukkitEconomyService();
        this.services.put(EconomyService.class, economyService);
        this.services.put(BukkitEconomyService.class, economyService);

        BukkitHologramService hologramService = new BukkitHologramService(this);
        this.services.put(HologramService.class, hologramService);
        this.services.put(BukkitHologramService.class, hologramService);

        Bukkit.getPluginManager().registerEvents(this.commandManager, this.plugin());
        Bukkit.getPluginManager().registerEvents(this, this.plugin());
    }

    @Override
    public void disable() {
        if (this.injector != null && !this.injector.isClosed())
            this.injector.close();
        this.services.clear();
        HandlerList.unregisterAll(this.commandManager);
        HandlerList.unregisterAll(this);
    }

    @Override
    public @NotNull BukkitPlayer player(UUID uuid) {
        return BukkitPlayer.of(uuid);
    }

}
