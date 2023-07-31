package ltd.kumo.plutomc.modules.whitelist.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.kumo.plutomc.modules.whitelist.WhitelistModule;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("unused")
public final class ProfileUtil {
    private ProfileUtil() {

    }

    public static UUID getUUID(String name) throws IOException {
        String requestUrl = "https://api.mojang.com/users/profiles/minecraft/" + name;
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();
        Call call = WhitelistModule.getHttpClient().newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();

        if (responseBody != null) {
            String responseBodyString = responseBody.string();
            JsonObject jsonObject = JsonParser.parseString(responseBodyString).getAsJsonObject();
            return trimmedUUIDtoFull(jsonObject.get("id").getAsString());
        }

        return null;
    }

    public static UUID trimmedUUIDtoFull(String uuid) {
        return UUID.fromString(uuid.length() == 32 ? uuid.substring(0, 8) + '-' + uuid.substring(8, 12) + '-' + uuid.substring(12, 16) + '-' + uuid.substring(16, 20) + '-' + uuid.substring(20) : uuid);
    }
}
