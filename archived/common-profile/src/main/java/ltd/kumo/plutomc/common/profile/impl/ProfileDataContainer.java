package ltd.kumo.plutomc.common.profile.impl;

import lombok.Getter;
import ltd.kumo.plutomc.common.profile.api.DataContainer;
import ltd.kumo.plutomc.common.profile.api.Profile;
import ltd.kumo.plutomc.common.profile.api.ProfileManager;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
public class ProfileDataContainer implements DataContainer {
    @NotNull
    @Getter
    private final Profile profile;

    @NotNull
    @Getter
    private final Document document;

    @NotNull
    @Getter
    private final ProfileManager profileManager;

    public ProfileDataContainer(@NotNull final ProfileManager profileManager, @NotNull final Profile profile, @NotNull final Document document) {
        Objects.requireNonNull(profileManager);
        Objects.requireNonNull(profile);
        Objects.requireNonNull(document);

        this.profileManager = profileManager;
        this.profile = profile;
        this.document = document;
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        if (!document.containsKey(key)) {
            document.append("custom." + key, value);
            return;
        }

        document.replace("custom." + key, value);
    }

    @Override
    @NotNull
    public Optional<String> getString(@NotNull String key) {
        return Optional.of(document.getString("custom." + key));
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        Objects.requireNonNull(key);
        return document.getBoolean("custom." + key);
    }

    @Override
    public int getInteger(@NotNull String key) {
        Objects.requireNonNull(key);
        return document.getInteger("custom." + key);
    }

    @Override
    public long getLong(@NotNull String key) {
        Objects.requireNonNull(key);
        return document.getLong("custom." + key);
    }

    @Override
    public double getDouble(@NotNull String key) {
        Objects.requireNonNull(key);
        return document.getDouble("custom." + key);
    }

    @Override
    public @NotNull Optional<Date> getDate(@NotNull String key) {
        Objects.requireNonNull(key);
        return Optional.of(document.getDate("custom." + key));
    }

    @Override
    public @NotNull Optional<Object> get(@NotNull String key) {
        Objects.requireNonNull(key);
        return Optional.of(document.get("custom." + key));
    }

    @Override
    public @NotNull <T> Optional<List<T>> getList(@NotNull String key, @NotNull Class<T> clazz) {
        Objects.requireNonNull(key);
        return Optional.of(document.getList("custom." + key, clazz));
    }

    @Override
    public void remove(@NotNull String key) {
        document.remove("custom." + key);
    }

    @Override
    public void apply() {
        profileManager.updateCustomData(profile, this);
    }
}
