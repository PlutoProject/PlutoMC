package ltd.kumo.plutomc.bot.qq.utilities

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText

object MessageUtility {

    @OptIn(DelicateCoroutinesApi::class)
    fun send(sender: CommandSender, message: String) {
        GlobalScope.launch {
            if (sender is MemberCommandSender) {
                sender.sendMessage(At(sender.user.id).plus(PlainText(" ")).plus(message))
                return@launch
            }
            sender.sendMessage(message)
        }
    }

}