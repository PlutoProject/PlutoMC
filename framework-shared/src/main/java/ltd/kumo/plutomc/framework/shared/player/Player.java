package ltd.kumo.plutomc.framework.shared.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public abstract class Player<T> {
    @NotNull UUID player;

    public Player(@NotNull UUID player) {
        Objects.requireNonNull(player);
        this.player = player;
    }

    public @Nullable
    abstract T player();

    public @NotNull UUID uuid() {
        return player;
    }
}
