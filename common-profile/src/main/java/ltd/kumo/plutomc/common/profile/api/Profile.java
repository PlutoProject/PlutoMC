package ltd.kumo.plutomc.common.profile.api;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface Profile {
    @NotNull
    Optional<DataContainer> getCustomDataContainer();

    @NotNull
    String name();

    @NotNull
    UUID uuid();
}
