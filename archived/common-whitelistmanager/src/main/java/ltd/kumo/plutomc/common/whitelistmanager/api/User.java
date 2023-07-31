package ltd.kumo.plutomc.common.whitelistmanager.api;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings("unused")
public interface User {
    @NotNull
    String getName();

    long getQQNumber();

    @NotNull
    UUID getUUID();

    void updateName(@NotNull String name);

    void updateQQNumber(int qqNumber);

    void updateUUID(@NotNull UUID uuid);
}
