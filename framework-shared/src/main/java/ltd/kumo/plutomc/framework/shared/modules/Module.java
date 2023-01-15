package ltd.kumo.plutomc.framework.shared.modules;

import ltd.kumo.plutomc.framework.shared.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class Module {

    @NotNull
    public Platform<?> platform;

    public Module(@NotNull Platform<?> platform) {
        Objects.requireNonNull(platform);
        this.platform = platform;
    }

    @NotNull
    public abstract String name();

    public abstract boolean shouldBeEnabled();

    public abstract void initial();

    public abstract void terminate();

    public abstract void reload();

    @NotNull
    public abstract Logger logger();
}
