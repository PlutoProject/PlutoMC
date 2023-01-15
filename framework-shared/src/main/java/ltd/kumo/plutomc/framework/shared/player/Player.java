package ltd.kumo.plutomc.framework.shared.player;

import ltd.kumo.plutomc.framework.shared.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public interface Player<T> extends CommandSender {

    @Nullable
    T player();

    @NotNull UUID uuid();

}
