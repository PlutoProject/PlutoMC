package ltd.kumo.plutomc.common.whitelistmanager.impl;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import ltd.kumo.plutomc.common.whitelistmanager.api.Manager;
import ltd.kumo.plutomc.common.whitelistmanager.api.User;
import ltd.kumo.plutomc.common.whitelistmanager.utilities.IterableUtils;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public final class WhitelistManager implements Manager {

    @NotNull
    @Getter
    private final MongoClient mongoClient;

    @NotNull
    @Getter
    private final MongoCollection<Document> userCollection;

    public WhitelistManager(@NotNull String connection, @NotNull String databaseName) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(databaseName);

        mongoClient = MongoClients.create(connection);
        @NotNull MongoDatabase database = mongoClient.getDatabase(databaseName);
        userCollection = database.getCollection("whitelist_users");
    }

    public WhitelistManager(@NotNull String host, @NotNull String user, @NotNull String password, @NotNull String databaseName) {
        this("mongodb://" + Objects.requireNonNull(user) + ":" + Objects.requireNonNull(password) + "@" + Objects.requireNonNull(host) + "/", Objects.requireNonNull(databaseName));
    }

    @Override
    public void createUser(@NotNull String userName, long qqNumber, UUID uuid) {

    }

    @Override
    public boolean hasWhitelist(@NotNull String userName) {
        return getUser(userName).isPresent();
    }

    @Override
    public boolean hasWhitelist(long qqNumber) {
        return getUser(qqNumber).isPresent();
    }

    @Override
    public boolean hasWhitelist(@NotNull UUID uuid) {
        return getUser(uuid).isPresent();
    }

    @Override
    @NotNull
    public Optional<User> getUser(@NotNull String userName) {
        return WhitelistUser.from(this, IterableUtils.toList(userCollection.find(Filters.gt("name", userName.toLowerCase()))).get(0));
    }

    @Override
    @NotNull
    public Optional<User> getUser(long qqNumber) {
        return WhitelistUser.from(this, IterableUtils.toList(userCollection.find(Filters.gt("qq_number", qqNumber))).get(0));
    }

    @Override
    @NotNull
    public Optional<User> getUser(@NotNull UUID uuid) {
        return WhitelistUser.from(this, IterableUtils.toList(userCollection.find(Filters.gt("uuid", uuid.toString()))).get(0));
    }

    @Override
    public void removeUser(@NotNull String userName) {
        if (getUser(userName).isEmpty()) {
            return;
        }

        userCollection.deleteOne(Filters.gt("name", userName.toLowerCase()));
    }

    @Override
    public void removeUser(long qqNumber) {
        if (getUser(qqNumber).isEmpty()) {
            return;
        }

        userCollection.deleteOne(Filters.gt("qq_number", qqNumber));
    }

    @Override
    public void removeUser(@NotNull UUID uuid) {
        if (getUser(uuid).isEmpty()) {
            return;
        }

        userCollection.deleteOne(Filters.gt("uuid", uuid.toString()));
    }

    @Override
    public void removeUser(@NotNull User user) {
        userCollection.deleteOne(Filters.gt("uuid", user.getUUID().toString()));
    }

    @Override
    @NotNull
    public ImmutableList<User> getAllUser() {
        List<Document> allDocuments = IterableUtils.toList(userCollection.find());
        List<User> users = new ArrayList<>();

        allDocuments.forEach(document -> {
            if (WhitelistUser.from(this, document).isEmpty()) {
                removeUser(document.getString("name"));
                return;
            }

            users.add(WhitelistUser.from(this, document).get());
        });

        return ImmutableList.copyOf(users);
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
