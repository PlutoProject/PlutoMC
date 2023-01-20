package ltd.kumo.plutomc.common.whitelistmanager.impl;

import com.google.common.collect.ImmutableList;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;
import lombok.Getter;
import ltd.kumo.plutomc.common.whitelistmanager.api.Manager;
import ltd.kumo.plutomc.common.whitelistmanager.api.User;
import ltd.kumo.plutomc.common.whitelistmanager.utilities.IterableUtils;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public final class WhitelistManager implements Manager, ServerMonitorListener {

    @NotNull
    @Getter
    private final MongoClient mongoClient;

    @NotNull
    @Getter
    private final MongoDatabase database;

    @NotNull
    @Getter
    private final MongoCollection<Document> userCollection;

    private boolean status = false;

    public WhitelistManager(@NotNull String connection, @NotNull String databaseName) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(databaseName);

        this.mongoClient = MongoClients.create(connection);
        this.database = this.mongoClient.getDatabase(databaseName);
        if (!this.database.listCollectionNames().into(new ArrayList<>()).contains("whitelist_users")) {
            this.database.createCollection("whitelist_users");
        }
        this.userCollection = this.database.getCollection("whitelist_users");
    }

    public WhitelistManager(@NotNull String host, int port, @NotNull String user, @NotNull String password, @NotNull String databaseName) {
        this("mongodb://" + Objects.requireNonNull(user) + ":" + Objects.requireNonNull(password) + "@" + Objects.requireNonNull(host) + ":" + port + "/?authSource=" + databaseName, databaseName);
    }

    @Override
    public void createUser(@NotNull String userName, long qqNumber, @NotNull UUID uuid) {
        @NotNull final Document document = new Document();

        document.append("name", userName);
        document.append("qq_number", qqNumber);
        document.append("uuid", uuid.toString());

        userCollection.insertOne(document);
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
        var list = IterableUtils.toList(userCollection.find(Filters.gt("name", userName.toLowerCase())));

        if (list.size() != 1) {
            return Optional.empty();
        }

        return WhitelistUser.from(this, list.get(0));
    }

    @Override
    @NotNull
    public Optional<User> getUser(long qqNumber) {
        var list = IterableUtils.toList(userCollection.find(Filters.gt("qq_number", qqNumber)));

        if (list.size() != 1) {
            return Optional.empty();
        }

        return WhitelistUser.from(this, list.get(0));
    }

    @Override
    @NotNull
    public Optional<User> getUser(@NotNull UUID uuid) {
        var list = IterableUtils.toList(userCollection.find(Filters.gt("uuid", uuid.toString())));

        if (list.size() != 1) {
            return Optional.empty();
        }

        return WhitelistUser.from(this, list.get(0));
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

    @Override
    public boolean connected() {
        return this.status;
    }

    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent event) {
        this.status = true;
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent event) {
        this.status = true;
    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent event) {
        this.status = false;
    }

}
