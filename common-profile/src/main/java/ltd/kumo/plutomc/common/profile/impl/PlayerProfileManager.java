package ltd.kumo.plutomc.common.profile.impl;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.event.ServerMonitorListener;
import lombok.Getter;
import ltd.kumo.plutomc.common.profile.api.Profile;
import ltd.kumo.plutomc.common.profile.api.ProfileManager;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public final class PlayerProfileManager implements ProfileManager, ServerMonitorListener {
    @Nullable
    @Getter
    private static MongoClient mongoClient;

    @Nullable
    @Getter
    private static MongoDatabase database;

    @Nullable
    @Getter
    private static MongoCollection<Document> profileCollection;

    public PlayerProfileManager(@NotNull String connection, @NotNull String databaseName) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(databaseName);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connection))
                .applyToServerSettings(builder -> builder.addServerMonitorListener(PlayerProfileManager.this))
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(databaseName);

        if (!database.listCollectionNames().into(new ArrayList<>()).contains("profile")) {
            database.createCollection("profile");
        }

        profileCollection = database.getCollection("profile");
    }

    public PlayerProfileManager(@NotNull String host, int port, @NotNull String user, @NotNull String password, @NotNull String databaseName) {
        this("mongodb://" + Objects.requireNonNull(user) + ":" + Objects.requireNonNull(password) + "@" + Objects.requireNonNull(host) + ":" + port + "/?authSource=" + databaseName, databaseName);
    }

    @Override
    public void createProfile(String name) {

    }

    @Override
    public void createProfile(@NotNull UUID uuid) {

    }

    @Override
    public void createProfile(@NotNull String name, @NotNull UUID uuid) {

    }

    @Override
    public @NotNull Profile getProfile(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull Profile getProfile(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public boolean contains(@NotNull String name) {
        return false;
    }

    @Override
    public boolean contains(@NotNull UUID uuid) {
        return false;
    }

    @Override
    public void updateName(@NotNull UUID uuid, String name) {

    }
}
