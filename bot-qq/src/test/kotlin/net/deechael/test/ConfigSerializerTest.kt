package net.deechael.test

import com.google.gson.Gson
import ltd.kumo.plutomc.bot.qq.PlutoQQBotConfig

fun main() {
    println(Gson().toJson(PlutoQQBotConfig()))
}