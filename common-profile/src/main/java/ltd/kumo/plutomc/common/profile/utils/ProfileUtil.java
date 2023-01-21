package ltd.kumo.plutomc.common.profile.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public final class ProfileUtil {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    private ProfileUtil() {
    }

    public static Optional<String> getNameByUUID(UUID uuid) {
        try {
            String url = "https://api.minetools.eu/profile/" + uuid.toString().toLowerCase();

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
            JsonObject jsonObject = JsonParser.parseString(responseStr).getAsJsonObject();

            return Optional.of(jsonObject.get("decoded.profileName").getAsString());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<UUID> getUUIDByName(String name) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + name.toLowerCase();

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
            JsonObject jsonObject = JsonParser.parseString(responseStr).getAsJsonObject();

            return Optional.of(UUID.fromString(trimmedToFull(jsonObject.get("id").getAsString())));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String trimmedToFull(String uuid) {
        return uuid.length() == 32 ? uuid.substring(0, 8) + '-' + uuid.substring(8, 12) + '-' + uuid.substring(12, 16) + '-' + uuid.substring(16, 20) + '-' + uuid.substring(20) : uuid;
    }
}
