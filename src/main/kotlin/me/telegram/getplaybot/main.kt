package me.telegram.getplaybot

import me.telegram.getplaybot.challenge.bot.ChallengeHandlers
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException

fun main(args: Array<String>) {
    try {
        ApiContextInitializer.init()
        val telegram = TelegramBotsApi()
        telegram.registerBot(ChallengeHandlers())
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}
