package cc.keyimc.keyi.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
public final class ConfigHelper {

    @NotNull
    @Getter
    private final CommentedFileConfig fileConfig;

    public ConfigHelper(@NotNull File file) {
        Objects.requireNonNull(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileConfig = CommentedFileConfig.ofConcurrent(file);

        fileConfig.load();
        fileConfig.save();
    }

    @NotNull
    public <T> T get(@NotNull String key, @NotNull T defaultValue) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(defaultValue);

        if (fileConfig.contains(key)) {
            return fileConfig.get(key);
        }

        fileConfig.set(key, defaultValue);
        return defaultValue;
    }

    @NotNull
    public <T> Optional<T> getOptional(@NotNull String key, @NotNull T defaultValue) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(defaultValue);

        if (fileConfig.contains(key)) {
            return fileConfig.getOptional(key);
        }

        fileConfig.set(key, defaultValue);
        return Optional.of(defaultValue);
    }

    public <T> void set(@NotNull String key, @Nullable T value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        fileConfig.set(key, value);
    }
}
