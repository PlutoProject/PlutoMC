package ltd.kumo.plutomc.framework.metadata;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 抽象元数据容器。
 * 临时使用，待重构。
 */
@SuppressWarnings("unused")
public abstract class AbstractMetaContainer {
    @NotNull
    public abstract Optional<String> getOptional(@NotNull String key);

    @NotNull
    public abstract CompletableFuture<Optional<String>> getOptionalAsync(@NotNull String key);

    @Nullable
    public abstract String get(@NotNull String key);

    @Nullable
    public abstract CompletableFuture<String> getAsync(@NotNull String key);

    public abstract void set(@NotNull String key, @NotNull Object object);

    public abstract void set(@NotNull String key, @NotNull String s);

    public abstract boolean contain(@NotNull String key);

    @NotNull
    public abstract String getWithDefault(@NotNull String key, @NotNull String defaultValue);

    @NotNull
    public abstract CompletableFuture<String> getWithDefaultAsync(@NotNull String key, @NotNull String defaultValue);

    public abstract void remove(String key);

    public abstract void apply();
}
