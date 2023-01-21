package ltd.kumo.plutomc.common.profile.impl;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.event.ServerMonitorListener;
import lombok.Getter;
import ltd.kumo.plutomc.common.profile.api.DataContainer;
import ltd.kumo.plutomc.common.profile.api.Profile;
import ltd.kumo.plutomc.common.profile.api.ProfileManager;
import ltd.kumo.plutomc.common.profile.utils.ProfileUtil;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
    public void createProfile(@NotNull String name) {
        var uuid = ProfileUtil.getUUIDByName(name.toLowerCase());

        if (uuid.isEmpty()) {
            return;
        }

        createProfile(name.toLowerCase(), uuid.get());
    }

    @Override
    public void createProfile(@NotNull UUID uuid) {
        var name = ProfileUtil.getNameByUUID(uuid);

        if (name.isEmpty()) {
            return;
        }

        createProfile(name.get().toLowerCase(), uuid);
    }

    @Override
    public void createProfile(@NotNull String name, @NotNull UUID uuid) {
        Objects.requireNonNull(profileCollection);
        Objects.requireNonNull(name);
        Objects.requireNonNull(uuid);

        Document document = new Document();
        document.append("name", name);
        document.append("uuid", uuid.toString().toLowerCase());

        profileCollection.insertOne(document);
    }

    @Override
    @NotNull
    public Optional<Profile> getProfile(@NotNull UUID uuid) {
        Objects.requireNonNull(profileCollection);
        Objects.requireNonNull(uuid);

        if (!contains(uuid)) {
            createProfile(uuid);
        }

        List<Document> documents = profileCollection.find(Filters.eq("uuid", uuid.toString().toLowerCase())).into(new ArrayList<>());

        if (documents.size() != 1) {
            return Optional.empty();
        }

        Document document = documents.get(0);

        return Optional.of(new PlayerProfile(this, document.getString("name"), UUID.fromString(document.getString("uuid"))));
    }

    @Override
    public boolean contains(@NotNull UUID uuid) {
        Objects.requireNonNull(profileCollection);
        Objects.requireNonNull(uuid);

        List<Document> documents = profileCollection.find(Filters.eq("uuid", uuid.toString().toLowerCase())).into(new ArrayList<>());

        return documents.size() == 1;
    }

    @Override
    public void updateName(@NotNull UUID uuid, @NotNull String name) {
        Objects.requireNonNull(profileCollection);

        if (!contains(uuid)) {
            createProfile(uuid);
        }

        profileCollection.updateOne(Filters.eq("uuid", uuid.toString().toLowerCase()), Updates.set("name", name.toLowerCase()));
    }

    @Override
    public void updateName(@NotNull Profile profile, @NotNull String name) {
        updateName(profile.uuid(), name);
    }

    @Override
    public void updateCustomData(@NotNull UUID uuid, @NotNull ProfileDataContainer profileDataContainer) {
        Objects.requireNonNull(profileCollection);
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(profileDataContainer);

        if (!contains(uuid)) {
            createProfile(uuid);
        }

        profileCollection.replaceOne(Filters.eq("uuid", uuid.toString().toLowerCase()), profileDataContainer.getDocument());
    }

    @Override
    public void updateCustomData(@NotNull Profile profile, @NotNull ProfileDataContainer profileDataContainer) {
        updateCustomData(profile.uuid(), profileDataContainer);
    }

    @Override
    public @NotNull Optional<DataContainer> getCustomDataContainer(@NotNull UUID uuid) {
        List<Document> documents = profileCollection.find(Filters.eq("uuid", uuid.toString().toLowerCase())).into(new ArrayList<>());

        if (documents.size() != 1 && getProfile(uuid).isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ProfileDataContainer(this, getProfile(uuid).get(), documents.get(0)));
    }

    @Override
    public @NotNull Optional<DataContainer> getCustomDataContainer(@NotNull Profile profile) {
        return getCustomDataContainer(profile.uuid());
    }
}
