package ltd.kumo.plutomc.common.profile.api;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface DataContainer {
    void set(@NotNull String key, @NotNull Object value);

    @NotNull
    Optional<String> getString(@NotNull String key);

    boolean getBoolean(@NotNull String key);

    int getInteger(@NotNull String key);

    long getLong(@NotNull String key);

    double getDouble(@NotNull String key);

    @NotNull
    Optional<Date> getDate(@NotNull String key);

    @NotNull
    Optional<Object> get(@NotNull String key);

    @NotNull
    <T> Optional<List<T>> getList(@NotNull String key, @NotNull Class<T> clazz);

    void remove(@NotNull String key);

    void apply();
}
