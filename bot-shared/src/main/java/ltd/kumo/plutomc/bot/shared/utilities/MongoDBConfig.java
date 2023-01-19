package ltd.kumo.plutomc.bot.shared.utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.deechael.dutil.gson.JOReader;

import java.lang.reflect.Type;

public class MongoDBConfig {

    public String host = "localhost";
    public int port = 27017;
    public String user = "user";
    public String password = "123456";
    public String database = "whitelist";

    public static final class Deserializer implements JsonDeserializer<MongoDBConfig> {

        @Override
        public MongoDBConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject())
                throw new JsonParseException("Must be a json object");
            MongoDBConfig config = new MongoDBConfig();
            JOReader reader = new JOReader(json.getAsJsonObject());
            config.host = reader.string("localhost");
            config.port = reader.intNumber("localhost");
            config.user = reader.string("localhost");
            config.password = reader.string("localhost");
            config.database = reader.string("localhost");
            return config;
        }

    }

}
