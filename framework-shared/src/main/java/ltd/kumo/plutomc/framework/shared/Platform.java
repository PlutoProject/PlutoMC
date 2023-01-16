package ltd.kumo.plutomc.framework.shared;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.shared.command.Command;
import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import ltd.kumo.plutomc.framework.shared.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Platform<T> {
    @NotNull T plugin;
    @NotNull ImmutableList<Module> modules;

    public Platform(@NotNull T plugin) {
        Objects.requireNonNull(plugin);

        this.plugin = plugin;
        this.modules = ImmutableList.of();
    }

    public @NotNull T plugin() {
        return plugin;
    }

    public @NotNull
    abstract ImmutableList<?> onlinePlayers();

    public @NotNull
    abstract String name();

    public @NotNull
    abstract String version();

    public @NotNull ImmutableList<Module> modules() {
        return modules;
    }

    public void modules(ImmutableList<Module> modules) {
        this.modules = Objects.requireNonNull(modules);
    }

    public abstract <E extends CommandSender, P extends Player<?>> Command<E, P> createCommand(String name);

    public abstract <E extends CommandSender, P extends Player<?>> void registerCommand(String prefix, Command<E, P> command);

    public abstract <E extends Service<E>> E getService(Class<E> clazz);

    public abstract void enableModules();

    public abstract void disableModules();

    public abstract void reloadModules();

    public abstract void load();
    public abstract void enable();
    public abstract void disable();

}
