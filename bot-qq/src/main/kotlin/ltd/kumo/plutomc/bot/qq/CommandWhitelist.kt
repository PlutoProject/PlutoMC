package ltd.kumo.plutomc.bot.qq

import ltd.kumo.plutomc.bot.qq.utilities.MessageUtility
import net.deechael.dutil.StrUtil
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.java.JRawCommand
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.SingleMessage
import java.lang.StringBuilder
import kotlin.math.min

object CommandWhitelist: JRawCommand(PlutoQQBot, "whitelist", "白名单") {

    override fun onCommand(sender: CommandSender, args: MessageChain) {
        val args = args.map(SingleMessage::contentToString)
        if (args.size == 1) {
            if (args[0] == "list") {
                val users = PlutoQQBot.whitelistManager!!.allUser
                val pages = if (users.size % 10 == 0) users.size / 10 else users.size / 10 + 1
                val builder = StringBuilder()

                var i = 0
                while (i < min(users.size, 10)) {
                    val user = users[i]
                    builder.append(user.qqNumber).append(" - ").append(user.name).append("\n")
                    i++
                }
                builder.append("===============\n").append("当前页数: ").append(1).append("  总页数: ").append(pages)

                MessageUtility.send(sender, builder.toString())
            } else {
                MessageUtility.send(sender, "哎呀，您使用了错误的用法或不存在的指令！")
            }
        } else if (args.size == 2) {
            if (args[0] == "list") {
                if (StrUtil.isInteger(args[1])) {
                    val users = PlutoQQBot.whitelistManager!!.allUser
                    val pages = if (users.size % 10 == 0) users.size / 10 else users.size / 10 + 1
                    var page = args[1].toInt()
                    if (page < 0)
                        page = 0
                    if (page > pages)
                        page = pages

                    val builder = StringBuilder()
                    var i = 10 * (page - 1)
                    while (i < min(users.size, 10 * page)) {
                        val user = users[i]
                        builder.append(user.qqNumber).append(" - ").append(user.name).append("\n")
                        i++
                    }
                    builder.append("===============\n").append("当前页数: ").append(page).append("  总页数: ").append(pages)

                    MessageUtility.send(sender, builder.toString())
                } else {
                    MessageUtility.send(sender, "")
                }
            } else {
                MessageUtility.send(sender, "哎呀，您使用了错误的用法或不存在的指令！")
            }
        } else {
            MessageUtility.send(sender, "哎呀，您使用了错误的用法或不存在的指令！")
        }
    }

}