package ltd.kumo.plutomc.common.whitelistmanager.api;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public interface Manager {
    void createUser(@NotNull String userName, long qqNumber, UUID uuid);

    boolean hasWhitelist(@NotNull String userName);

    boolean hasWhitelist(long qqNumber);

    boolean hasWhitelist(@NotNull UUID uuid);

    @NotNull
    Optional<User> getUser(@NotNull String userName);

    @NotNull
    Optional<User> getUser(long qqNumber);

    @NotNull
    Optional<User> getUser(@NotNull UUID uuid);

    void removeUser(@NotNull String userName);

    void removeUser(long qqNumber);

    void removeUser(@NotNull UUID uuid);

    void removeUser(@NotNull User user);

    @NotNull
    ImmutableList<User> getAllUser();

    void close();
}
