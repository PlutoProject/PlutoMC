package ltd.kumo.plutomc.common.whitelistmanager.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import ltd.kumo.plutomc.common.whitelistmanager.api.Manager;
import ltd.kumo.plutomc.common.whitelistmanager.api.User;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class WhitelistUser implements User {
    @NotNull
    private final Document document;

    @NotNull
    private final WhitelistManager manager;

    private WhitelistUser(@NotNull Manager manager, @NotNull Document document) {
        Objects.requireNonNull(document);
        Objects.requireNonNull(manager);

        this.manager = (WhitelistManager) manager;
        this.document = document;
    }

    @NotNull
    public static Optional<User> from(@NotNull Manager manager, @Nullable Document document) {
        if (document == null) {
            return Optional.empty();
        }

        return Optional.of(new WhitelistUser(manager, document));
    }

    @Override
    @NotNull
    public String getName() {
        return document.getString("name");
    }

    @Override
    public long getQQNumber() {
        return document.getLong("qq_number");
    }

    @Override
    public @NotNull UUID getUUID() {
        return UUID.fromString(document.getString("uuid"));
    }

    @Override
    public void updateName(@NotNull String name) {
        manager.getUserCollection().updateOne(Filters.gt("name", getName()), Updates.set("name", name));
    }

    @Override
    public void updateQQNumber(int qqNumber) {
        manager.getUserCollection().updateOne(Filters.gt("qq_number", getQQNumber()), Updates.set("qq_number", qqNumber));
    }

    @Override
    public void updateUUID(@NotNull UUID uuid) {
        manager.getUserCollection().updateOne(Filters.gt("uuid", getUUID()), Updates.set("uuid", uuid.toString()));
    }
}
