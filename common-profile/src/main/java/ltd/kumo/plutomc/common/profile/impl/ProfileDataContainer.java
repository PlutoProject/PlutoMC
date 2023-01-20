package ltd.kumo.plutomc.common.profile.impl;

import ltd.kumo.plutomc.common.profile.api.DataContainer;
import ltd.kumo.plutomc.common.profile.api.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
public class ProfileDataContainer implements DataContainer {
    @NotNull
    private final Profile PROFILE;

    private ProfileDataContainer(@NotNull final Profile profile) {
        Objects.requireNonNull(profile);
        this.PROFILE = profile;
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value) {

    }

    @Override
    public @NotNull Optional<String> getString(@NotNull String key) {
        return Optional.empty();
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return false;
    }

    @Override
    public int getInteger(@NotNull String key) {
        return 0;
    }

    @Override
    public long getLong(@NotNull String key) {
        return 0;
    }

    @Override
    public @NotNull Optional<Object> get(@NotNull String key) {
        return Optional.empty();
    }

    @Override
    public @NotNull <T> Optional<List<T>> getList(@NotNull String key, @NotNull Class<T> clazz) {
        return Optional.empty();
    }

    @Override
    public void remove(String string) {

    }

    @NotNull
    public static DataContainer from(@NotNull Profile profile) {
        return new ProfileDataContainer(profile);
    }
}
