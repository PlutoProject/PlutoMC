package ltd.kumo.plutomc.bot.qq

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import ltd.kumo.plutomc.bot.shared.utilities.FileUtility
import ltd.kumo.plutomc.bot.shared.utilities.MongoDBConfig
import ltd.kumo.plutomc.common.whitelistmanager.impl.WhitelistManager
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.MemberLeaveEvent
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object PlutoQQBot : JavaPlugin(JvmPluginDescriptionBuilder("ltd.kumo.plutomc.bot", "0.1.0")
        .name("bot-qq")
        .author("DeeChael")
        .build()) {

    private val GSON = GsonBuilder()
            .registerTypeAdapter(MongoDBConfig::class.java, MongoDBConfig.Deserializer())
            .setPrettyPrinting()
            .create()

    val EXECUTOR_SERVICE: ExecutorService = Executors.newFixedThreadPool(4)
    private var bot: Bot? = null
    var whitelistManager: WhitelistManager? = null
        private set
    private var loginSubscriber: Listener<BotOnlineEvent>? = null
    private var memberLeaveListener: Listener<MemberLeaveEvent>? = null
    private var config: PlutoQQBotConfig? = null

    override fun onEnable() {
        if (!getConfigurationFile().exists()) {
            saveDefaultConfiguration()
            logger.warning("配置文件不存在，已生成默认配置文件，请重新启动")
            return
        }
        config = configuration()
        // if (bot == null) {
        //     bot = BotFactory.newBot(config!!.account, config!!.password)
        // }
        // if (!bot!!.isOnline) {
        //     launch {
        //         logger.info("正常尝试登录账号")
        //         try {
        //             bot!!.login()
        //         } catch (e: WrongPasswordException) {
        //             logger.error("密码错误！", e)
        //         }
        //     }
        // }
        if (loginSubscriber == null) {
            loginSubscriber = GlobalEventChannel.subscribeAlways { event ->
                if (event.bot.id == config!!.account) {
                    PlutoQQBot.bot = event.bot
                    logger.info("账号登录成功")
                    if (whitelistManager == null) {
                        val mongo = config!!.mongo
                        whitelistManager = WhitelistManager(
                                mongo.host,
                                mongo.port,
                                mongo.user,
                                mongo.password,
                                mongo.password
                        )
                        if (!whitelistManager!!.connected()) {
                            logger.error("Failed to connect to the mongo db", RuntimeException("Failed to connect to the mongo db"))
                        }
                    }
                }
            }
        }
        if (memberLeaveListener == null) {
            memberLeaveListener = GlobalEventChannel.subscribeAlways { event ->
                if (event.bot.id != config!!.account)
                    return@subscribeAlways
                val userId = event.user.id
                if (!whitelistManager!!.connected())
                    return@subscribeAlways
                if (whitelistManager!!.hasWhitelist(userId)) {
                    whitelistManager!!.removeUser(userId)
                }

            }
        }
        CommandManager.registerCommand(CommandWhitelist, true)
    }

    override fun onDisable() {
        if (loginSubscriber != null) {
            loginSubscriber!!.complete()
            loginSubscriber = null
        }
        if (memberLeaveListener != null) {
            memberLeaveListener!!.complete()
            memberLeaveListener = null
        }
        if (whitelistManager != null) {
            whitelistManager!!.close()
            whitelistManager = null
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
        val defaultConfiguration = GSON.toJson(PlutoQQBotConfig())
        FileUtility.write(getConfigurationFile(), defaultConfiguration)
    }

}