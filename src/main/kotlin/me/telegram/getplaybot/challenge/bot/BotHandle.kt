package me.telegram.getplaybot.challenge.bot

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.lib.replyWithCallbacks
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message

abstract class BotHandle(val name: String) {
    private fun indexOf(text: String) = text.toLowerCase().let {
        val prefix = "/$name"
        if (it == prefix || it.startsWith(prefix + " ")) prefix.length else 0
    }

    private fun getPayload(text: String) = text.trim().drop(indexOf(text)).trim()

    fun isDo(text: String) = indexOf(text) != 0

    fun replyCallbacks(message: SendMessage) = replyWithCallbacks(message, this.name)

    suspend fun run(message: Message, user: User, text: String) = execute(message, user, getPayload(text))

    abstract suspend fun execute(message: Message, user: User, payload: String)
}
