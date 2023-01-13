package ltd.kumo.plutomc.framework.shared;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.shared.modules.Module;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class Platform<T> {
    @NotNull T plugin;
    @NotNull ImmutableList<Module> modules;

    public Platform(@NotNull T plugin) {
        this.plugin = plugin;
        this.modules = ImmutableList.of();
    }

    public @NotNull T plugin() {
        return plugin;
    }

    public @NotNull abstract ImmutableList<?> onlinePlayers();

    public @NotNull abstract String name();

    public @NotNull abstract String version();

    public @NotNull ImmutableList<Module> modules() {
        return modules;
    }
    public void modules(ImmutableList<Module> modules) {
        this.modules = modules;
    }

    public abstract void enableModules();

    public abstract void disableModules();

    public abstract void reloadModules();
}
