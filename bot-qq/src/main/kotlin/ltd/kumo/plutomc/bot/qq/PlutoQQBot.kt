package ltd.kumo.plutomc.bot.qq

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import ltd.kumo.plutomc.bot.shared.utilities.FileUtility
import net.deechael.dutil.gson.JOBuilder
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

object PlutoQQBot : JavaPlugin(
    JvmPluginDescriptionBuilder("ltd.kumo.plutomc.bot", "0.1.0")
        .name("bot-qq")
        .author("DeeChael")
        .build()) {

    private val GSON = GsonBuilder().create()

    override fun onEnable() {
        if (!getConfigurationFile().exists()) {
            logger.warning("配置文件不存在，已生成默认配置文件，请重新启动")
            return
        }
    }

    fun configuration(): PlutoQQBotConfig {
        return GSON.fromJson(loadConfiguration(), PlutoQQBotConfig::class.java)
    }

    fun getConfigurationFile(): File {
        return File(dataFolder, "config.json")
    }

    fun loadConfiguration(): JsonObject {
        val reader = FileReader(getConfigurationFile(), StandardCharsets.UTF_8)
        val result = JsonParser.parseReader(reader).asJsonObject
        reader.close()
        return result
    }

    fun saveDefaultConfiguration() {
        val defaultConfiguration = GSON.toJson(JOBuilder.of()
                .number("account", 0L)
                .string("password", "123456")
                .build())
        FileUtility.write(getConfigurationFile(), defaultConfiguration)
    }

}