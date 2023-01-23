package ltd.kumo.plutomc.framework.bukkit;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandManager;
import ltd.kumo.plutomc.framework.bukkit.economy.BukkitEconomyService;
import ltd.kumo.plutomc.framework.bukkit.gui.Menu;
import ltd.kumo.plutomc.framework.bukkit.gui.impl.MenuImpl;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.injector.ProtocolInjector;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.bukkit.services.HologramService;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import ltd.kumo.plutomc.framework.shared.economy.EconomyService;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.bukkit.Bukkit;
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
        PlutoHologramsAPI.onLoad(this.plugin());
    }

    @Override
    public void enable() {
        this.injector = new ProtocolInjector(this);

        this.commandManager = new BukkitCommandManager(this);

        PlutoHologramsAPI.onEnable();
        this.services.put(HologramService.class, new HologramService());

        BukkitEconomyService economyService = new BukkitEconomyService();
        this.services.put(EconomyService.class, economyService);
        this.services.put(BukkitEconomyService.class, economyService);

        // Bukkit.getPluginManager().registerEvents(new CommandListeners(this), this.plugin());
        Bukkit.getPluginManager().registerEvents(this.commandManager, this.plugin());
        Bukkit.getPluginManager().registerEvents(this, this.plugin());
    }

    @Override
    public void disable() {
        if (this.injector != null && !this.injector.isClosed())
            this.injector.close();
        PlutoHologramsAPI.onDisable();
    }

    @Override
    public @NotNull BukkitPlayer player(UUID uuid) {
        return BukkitPlayer.of(uuid);
    }

}
