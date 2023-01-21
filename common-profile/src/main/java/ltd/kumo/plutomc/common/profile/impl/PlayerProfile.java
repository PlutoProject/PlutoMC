package ltd.kumo.plutomc.common.profile.impl;

import lombok.Getter;
import ltd.kumo.plutomc.common.profile.api.DataContainer;
import ltd.kumo.plutomc.common.profile.api.Profile;
import ltd.kumo.plutomc.common.profile.api.ProfileManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public final class PlayerProfile implements Profile {
    @NotNull
    private final String name;

    @NotNull
    private final UUID uuid;

    @NotNull
    @Getter
    private final ProfileManager profileManager;

    public PlayerProfile(@NotNull final ProfileManager profileManager, @NotNull final String name, @NotNull final UUID uuid) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(uuid);

        this.name = name;
        this.uuid = uuid;
        this.profileManager = profileManager;
    }

    @Override
    @NotNull
    public Optional<DataContainer> getCustomDataContainer() {
        return profileManager.getCustomDataContainer(this);
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull UUID uuid() {
        return uuid;
    }
}
