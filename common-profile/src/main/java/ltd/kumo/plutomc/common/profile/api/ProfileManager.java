package ltd.kumo.plutomc.common.profile.api;

import ltd.kumo.plutomc.common.profile.impl.ProfileDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ProfileManager {
    void createProfile(@NotNull String name);

    void createProfile(@NotNull UUID uuid);

    void createProfile(@NotNull String name, @NotNull UUID uuid);

    @NotNull
    Optional<Profile> getProfile(@NotNull UUID uuid);

    boolean contains(@NotNull UUID uuid);

    void updateName(@NotNull UUID uuid, @NotNull String name);

    void updateName(@NotNull Profile profile, @NotNull String name);

    void updateCustomData(@NotNull UUID uuid, @NotNull ProfileDataContainer profileDataContainer);

    void updateCustomData(@NotNull Profile profile, @NotNull ProfileDataContainer profileDataContainer);

    @NotNull
    Optional<DataContainer> getCustomDataContainer(@NotNull UUID uuid);

    @NotNull
    Optional<DataContainer> getCustomDataContainer(@NotNull Profile profile);
}
