package me.telegram.getplaybot

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.telegram.getplaybot.handles.*
import me.telegram.getplaybot.lib.getEnv
import me.telegram.getplaybot.lib.whenNotNull
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.users.get
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

typealias TextMessageReply = suspend (SendMessage) -> String
typealias MessageBody = suspend (SendMessage) -> Unit
typealias MessagePhotoBody = suspend (SendPhoto) -> Unit

data class Handler(val name: String) {
    fun isDo(text: String) = text == "/$name" || text.startsWith("/$name ")
    fun getPayload(text: String) = text.removePrefix("/$name").trim()
}

val handlers = listOf(
        Handler("start"),

        Handler("scores"),
        Handler("last"),

        Handler("me"),
        Handler("vote"),

        Handler("invite"),
        Handler("reg")
)

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
        val user = initUser(message) // @ToDo: lazy
        val handler = handlers.find { it.isDo(text) }

        if (handler != null) {
            when (handler.name) {
                "start" -> sendMDMessage(message) {
                    handleWelcome(user, message.from, handler.getPayload(text))
                }

                "scores" -> sendMDMessage(message) {
                    handleScores()
                }
                "last" -> sendMDMessage(message) {
                    handleMatchDay()
                }

                "me" -> sendMDMessage(message) {
                    handleMe(user)
                }
                "vote" -> sendMDMessage(message) {
                    handleVote(user)
                }

                "invite" -> sendMDMessage(message) {
                    handleRegisterInvite(user)
                }
                "reg" -> sendMDMessage(message) {
                    it.enableMarkdown(true)
                    handleRegisterApprove(user, handler.getPayload(text))
                }
            }
        }
    }

    suspend fun initUser(message: Message): User {
        val from = message.from
        return get(from.id) ?: User(from.id, message.chatId)
    }

    suspend fun sendImage(message: Message, body: MessagePhotoBody): Unit {
        val reply = SendPhoto()
        reply.setChatId(message.chatId)
        body(reply)
        sendPhoto(reply)
    }

    suspend fun send(message: Message, body: MessageBody): Unit {
        val reply = SendMessage()
        reply.setChatId(message.chatId)
        body(reply)
        sendMessage(reply)
    }

    suspend fun sendMDMessage(message: Message, body: TextMessageReply): Unit {
        send(message) {
            it.enableMarkdown(true)
            it.text = body(it)
        }
    }
}