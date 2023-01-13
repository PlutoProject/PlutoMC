package ltd.kumo.plutomc.framework.shared.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public abstract class Player<T> {
    public @NotNull UUID player;

    public Player(@NotNull UUID player) {
        this.player = player;
    }

    public @Nullable abstract T player();
}
