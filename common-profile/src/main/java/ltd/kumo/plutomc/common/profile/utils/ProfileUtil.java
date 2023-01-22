package ltd.kumo.plutomc.common.profile.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public final class ProfileUtil {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    private ProfileUtil() {
    }

    @NotNull
    public static Optional<String> getNameByUUID(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid);

        String url = "https://api.minetools.eu/profile/" + uuid.toString().toLowerCase();
        var jsonObject = request(url);
        return jsonObject.map(object -> object.get("decoded.profileName").getAsString());
    }

    @NotNull
    public static Optional<UUID> getUUIDByName(@NotNull String name) {
        Objects.requireNonNull(name);

        String url = "https://api.mojang.com/users/profiles/minecraft/" + name.toLowerCase();
        var jsonObject = request(url);
        return jsonObject.map(object -> UUID.fromString(trimmedToFull(object.get("id").getAsString())));
    }

    @NotNull
    public static String trimmedToFull(@NotNull String uuid) {
        Objects.requireNonNull(uuid);
        return uuid.length() == 32 ? uuid.substring(0, 8) + '-' + uuid.substring(8, 12) + '-' + uuid.substring(12, 16) + '-' + uuid.substring(16, 20) + '-' + uuid.substring(20) : uuid;
    }

    @NotNull
    private static Optional<JsonObject> request(@NotNull String url) {
        Objects.requireNonNull(url);

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = OK_HTTP_CLIENT.newCall(request);
            Response response = call.execute();
            ResponseBody responseBody = response.body();

            if (responseBody == null) {
                return Optional.empty();
            }

            String responseStr = responseBody.string();
            return Optional.of(JsonParser.parseString(responseStr).getAsJsonObject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
