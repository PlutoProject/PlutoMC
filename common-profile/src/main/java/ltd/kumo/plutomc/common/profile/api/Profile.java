package ltd.kumo.plutomc.common.profile.api;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Profile {
    @NotNull
    DataContainer getCustomDataContainer();

    @NotNull
    String name();

    @NotNull
    UUID uuid();
}
