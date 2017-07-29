package me.telegram.getplaybot

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.telegram.getplaybot.handles.handleRegisterApprove
import me.telegram.getplaybot.handles.handleRegisterInvite
import me.telegram.getplaybot.handles.handleWelcome
import me.telegram.getplaybot.lib.getEnv
import me.telegram.getplaybot.lib.whenNotNull
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.user.get
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

typealias TextMessageReply = suspend (SendMessage) -> String

class ChallengeHandlers : TelegramLongPollingBot() {
    override fun getBotToken(): String = getEnv("BOT_CHALLENGE_TOKEN")
    override fun getBotUsername(): String = getEnv("BOT_CHALLENGE_NAME")

    override fun onUpdateReceived(update: Update?) {
        launch(CommonPool) {
            whenNotNull(update?.message) {
                if (it.hasText()) handleIncomingTextMessage(it)
            }
        }
    }

    suspend fun handleIncomingTextMessage(message: Message) {
        val text = message.text.trim()

        val user = initUser(message)
        when {
            text == "/start" -> replyTextMessage(message) { handleWelcome(user, message.from) }
            text == "/invite" -> replyTextMessage(message) { handleRegisterInvite(user) }
            text.startsWith("/reg ") -> replyTextMessage(message) {
                handleRegisterApprove(text.removePrefix("/reg ").trim(), user)
            }
        }
    }

    suspend fun initUser(message: Message): User {
        val from = message.from
        return get(from.id) ?: User(from.id, message.chatId)
    }

    suspend fun replyTextMessage(message: Message, body: TextMessageReply): Unit {
        val replyMessage = SendMessage()
        replyMessage.setChatId(message.chatId)
        replyMessage.text = body(replyMessage)
        sendMessage(replyMessage)
    }
}