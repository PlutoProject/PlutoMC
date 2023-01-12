package ltd.kumo.plutomc.framework.player.metadata;

import ltd.kumo.plutomc.framework.AbstractPlatform;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 玩家元数据容器。
 * 临时使用，待重构。
 * @param <T> 玩家实例类型，根据平台来。
 */
@SuppressWarnings("all")
public class PlayerMetaContainer<T> extends AbstractMetaContainer{
    @NotNull
    T player;

    public PlayerMetaContainer(@NotNull T player) {
        this.player = player;
    }

    @Nullable
    private static <T> User getUser(@NotNull T player) {
        if (player instanceof Player) {
            return AbstractPlatform.getLuckPermsApi().getUserManager().getUser(((Player) player).getUniqueId());
        }

        if (player instanceof com.velocitypowered.api.proxy.Player) {
            return AbstractPlatform.getLuckPermsApi().getUserManager().getUser(((com.velocitypowered.api.proxy.Player) player).getUniqueId());
        }

        return null;
    }

    @Override
    public @NotNull Optional<String> getOptional(@NotNull String key) {
        return Optional.ofNullable(getUser(player).getCachedData().getMetaData().getMetaValue(key));
    }

    @Override
    public @NotNull CompletableFuture<Optional<String>> getOptionalAsync(@NotNull String key) {
        return CompletableFuture.supplyAsync(() -> getOptional(key));
    }

    @Override
    public @Nullable String get(@NotNull String key) {
        return getUser(player).getCachedData().getMetaData().getMetaValue(key);
    }

    @Override
    public @Nullable CompletableFuture<String> getAsync(@NotNull String key) {
        return CompletableFuture.supplyAsync(() -> get(key));
    }

    @Override
    public void set(@NotNull String key, @NotNull Object object) {
        set(key, object.toString());
    }

    @Override
    public void set(@NotNull String key, @NotNull String s) {
        MetaNode node = MetaNode.builder(key, s).build();
        getUser(player).data().clear(NodeType.META.predicate(metaNode -> metaNode.getMetaKey().equals(key)));
        getUser(player).data().add(node);
    }

    @Override
    public boolean contain(@NotNull String key) {
        return getOptional(key).isPresent();
    }

    @Override
    public @NotNull String getWithDefault(@NotNull String key, @NotNull String defaultValue) {
        if (!contain(key)) {
            set(key, defaultValue);
        }

        return defaultValue;
    }

    @Override
    public @NotNull CompletableFuture<String> getWithDefaultAsync(@NotNull String key, @NotNull String defaultValue) {
        return CompletableFuture.supplyAsync(() -> getWithDefault(key, defaultValue));
    }

    @Override
    public void remove(String key) {
        getUser(player).data().clear(NodeType.META.predicate(metaNode -> metaNode.getMetaKey().equals(key)));
    }

    @Override
    public void apply() {
        AbstractPlatform.getLuckPermsApi().getUserManager().saveUser(getUser(player));
    }
}
