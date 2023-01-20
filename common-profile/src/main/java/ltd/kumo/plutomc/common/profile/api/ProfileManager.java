package ltd.kumo.plutomc.common.profile.api;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface ProfileManager {
    void createProfile(String name);

    void createProfile(@NotNull UUID uuid);

    void createProfile(@NotNull String name, @NotNull UUID uuid);

    @NotNull
    Profile getProfile(@NotNull String name);

    @NotNull
    Profile getProfile(@NotNull UUID uuid);

    boolean contains(@NotNull String name);

    boolean contains(@NotNull UUID uuid);

    void updateName(@NotNull UUID uuid, String name);
}
