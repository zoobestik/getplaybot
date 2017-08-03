package me.telegram.getplaybot.challenge

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.telegram.getplaybot.challenge.handles.*
import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.services.users.get
import me.telegram.getplaybot.lib.getEnv
import me.telegram.getplaybot.lib.whenNotNull
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

typealias MessageBody = suspend (SendMessage) -> Unit
typealias MessageTextBody = suspend (SendMessage) -> String
typealias MessagePhotoBody = suspend (SendPhoto) -> Unit

abstract class TextHandle(val name: String) {
    abstract suspend fun execute(message: Message, user: User, payload: String)

    fun isDo(text: String) = text == "/$name" || text.startsWith("/$name ")
    fun getPayload(text: String) = text.removePrefix("/$name").trim()

    suspend fun run(message: Message, user: User, text: String) =
            execute(message, user, getPayload(text))
}

class ChallengeHandlers : TelegramLongPollingBot() {
    override fun getBotToken(): String = getEnv("BOT_CHALLENGE_TOKEN")
    override fun getBotUsername(): String = getEnv("BOT_CHALLENGE_NAME")

    override fun onUpdateReceived(update: Update?) {
        launch(CommonPool) {
            whenNotNull(update?.message) { message ->
                if (message.hasText()) handleIncomingTextMessage(message)
            }
        }
    }

    val handlers = listOf(
            object : TextHandle("start") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    if (payload.isEmpty()) handleWelcome(user, message.from)
                    else handleRegisterApprove(user, payload)
                }
            },

            object : TextHandle("scores") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleScores()
                }
            },
            object : TextHandle("last") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleMatchDay()
                }
            },

            object : TextHandle("me") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleMe(user)
                }
            },
            object : TextHandle("vote") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleVote(user)
                }
            },

            object : TextHandle("invite") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleRegisterInvite(user, botUsername)
                }
            },
            object : TextHandle("reg") {
                suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                    handleRegisterApprove(user, payload)
                }
            }
    )

    suspend fun handleIncomingTextMessage(message: Message) {
        val id = message.from.id
        val text = message.text.trim()
        val user = get(id) ?: User(id, message.chatId)
        val handle = handlers.find { it.isDo(text) } ?:
                return

        handle.run(message, user, text)
    }

    suspend fun send(message: Message, body: MessageBody): Unit {
        val reply = SendMessage()
        reply.setChatId(message.chatId)
        body(reply)
        sendMessage(reply)
    }

    suspend fun image(message: Message, body: MessagePhotoBody): Unit {
        val reply = SendPhoto()
        reply.setChatId(message.chatId)
        body(reply)
        sendPhoto(reply)
    }

    suspend fun markdown(message: Message, body: MessageTextBody): Unit {
        send(message) {
            it.enableMarkdown(true)
            it.text = body(it)
        }
    }
}
