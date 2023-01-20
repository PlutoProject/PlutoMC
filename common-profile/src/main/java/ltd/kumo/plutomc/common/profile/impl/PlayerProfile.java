package ltd.kumo.plutomc.common.profile.impl;

import ltd.kumo.plutomc.common.profile.api.DataContainer;
import ltd.kumo.plutomc.common.profile.api.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public final class PlayerProfile implements Profile {
    @NotNull
    private final String NAME;

    @NotNull
    private final UUID UUID;

    private PlayerProfile(@NotNull String name, @NotNull UUID uuid) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(uuid);

        this.NAME = name;
        this.UUID = uuid;
    }

    @Override
    public @NotNull DataContainer getCustomDataContainer() {
        return null;
    }

    @Override
    public @NotNull String name() {
        return NAME;
    }

    @Override
    public @NotNull UUID uuid() {
        return UUID;
    }
}
