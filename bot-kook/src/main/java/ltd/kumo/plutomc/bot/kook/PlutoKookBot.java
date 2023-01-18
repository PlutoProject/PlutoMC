package ltd.kumo.plutomc.bot.kook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.kumo.plutomc.bot.shared.utilities.FileUtility;
import net.deechael.dutil.gson.JOBuilder;
import snw.jkook.plugin.BasePlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class PlutoKookBot extends BasePlugin {

    private final static Gson GSON = new GsonBuilder().create();

    @Override
    public void onEnable() {

    }

    public PlutoKookBotConfig configuration() {
        return GSON.fromJson(loadConfiguration(), PlutoKookBotConfig.class);
    }

    public File configurationFile() {
        return new File(this.getDataFolder(), "config.json");
    }

    public JsonObject loadConfiguration() {
        try {
            FileReader reader = new FileReader(this.configurationFile(), StandardCharsets.UTF_8);
            JsonObject result = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDefaultConfigurationFile() {
        String defaultConfiguration = GSON.toJson(JOBuilder.of()
                .string("token", "")
                .build());
        FileUtility.write(configurationFile(), defaultConfiguration);
    }

}
