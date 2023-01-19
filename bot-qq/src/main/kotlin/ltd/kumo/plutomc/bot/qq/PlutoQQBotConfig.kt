package ltd.kumo.plutomc.bot.qq

import com.google.gson.annotations.SerializedName
import ltd.kumo.plutomc.bot.shared.utilities.MongoDBConfig

class PlutoQQBotConfig {

    var account: Long = 0
    // var password: String = ""

    @SerializedName("mongodb")
    var mongo: MongoDBConfig = MongoDBConfig()

}