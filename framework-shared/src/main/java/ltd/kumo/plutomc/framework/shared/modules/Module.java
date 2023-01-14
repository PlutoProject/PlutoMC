package ltd.kumo.plutomc.framework.shared.modules;

import ltd.kumo.plutomc.framework.shared.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Module {
    public @NotNull Platform<?> platform;

    public Module(@NotNull Platform<?> platform) {
        Objects.requireNonNull(platform);
        this.platform = platform;
    }

    public abstract @NotNull String name();

    public abstract boolean shouldBeEnabled();

    public abstract void initial();

    public abstract void terminate();

    public abstract void reload();
}
