package club.plutomc.plutoproject.framework.shared.player;

import club.plutomc.plutoproject.framework.shared.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public interface Player<T> extends CommandSender {

    @Nullable
    T player();

    @NotNull UUID uuid();

}
