package ltd.kumo.plutomc.framework.bukkit;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import ltd.kumo.plutomc.framework.bukkit.services.HologramService;
import ltd.kumo.plutomc.framework.shared.Platform;
import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "unchecked"})
public class BukkitPlatform extends Platform<JavaPlugin> {

    private final Map<Class<? extends Service<?>>, Service<?>> services = new HashMap<>();

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
    public <E extends CommandSender> Command<E> createCommand(String name) {
        // TODO
        return null;
    }

    @Override
    public <E extends CommandSender> void registerCommand(String prefix, Command<E> command) {
        // TODO
    }

    @Override
    public <E extends Service<E>> E getService(Class<E> clazz) {
        return (E) this.services.get(clazz);
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
        PlutoHologramsAPI.onEnable();
        this.services.put(HologramService.class, new HologramService());
    }

    @Override
    public void disable() {
        PlutoHologramsAPI.onDisable();
    }

}
