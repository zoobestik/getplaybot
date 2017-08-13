package me.telegram.getplaybot.challenge

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.handles.handleHelp
import me.telegram.getplaybot.challenge.handles.handleVote
import me.telegram.getplaybot.challenge.handles.handleWelcome
import me.telegram.getplaybot.challenge.handles.league.handleLeagues
import me.telegram.getplaybot.challenge.handles.league.handleNewLeague
import me.telegram.getplaybot.challenge.handles.registration.handleRegisterApprove
import me.telegram.getplaybot.challenge.handles.registration.handleRegisterInvite
import me.telegram.getplaybot.challenge.handles.stats.handleMatchDay
import me.telegram.getplaybot.challenge.handles.stats.handleMe
import me.telegram.getplaybot.challenge.handles.stats.handleScores
import me.telegram.getplaybot.challenge.labels.label
import me.telegram.getplaybot.challenge.services.users.get
import me.telegram.getplaybot.lib.getEnv
import me.telegram.getplaybot.lib.logger
import me.telegram.getplaybot.lib.replyWithCallbacks
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

private val logRequest = LoggerFactory.getLogger("ChallengeHandlersRequests")

typealias MessageBody = suspend (SendMessage) -> Unit
typealias MessageTextBody = suspend (SendMessage) -> String
typealias MessagePhotoBody = suspend (SendPhoto) -> Unit

abstract class TextHandle(val name: String) {
    abstract suspend fun execute(message: Message, user: User, payload: String)

    fun startsOf(text: String) = text.toLowerCase().let {
        val prefix = "/$name"
        if (it == prefix || it.startsWith("$prefix ")) prefix.length else 0
    }

    fun isDo(text: String) = startsOf(text) != 0
    fun getPayload(text: String) = text.drop(startsOf(text)).trim()
    fun replyCallbacks(message: SendMessage) = replyWithCallbacks(message, this.name)

    suspend fun run(message: Message, user: User, text: String) =
        execute(message, user, getPayload(text))
}

class ChallengeHandlers : TelegramLongPollingBot() {
    companion object {
        val log by logger()
    }

    override fun getBotToken(): String = getEnv("BOT_CHALLENGE_TOKEN")
    override fun getBotUsername(): String = getEnv("BOT_CHALLENGE_NAME")

    override fun onUpdateReceived(update: Update?) {
        logRequest.info("New update received with {}", update)
        launch(CommonPool) {
            if (update != null) {
                val millisStarted = System.currentTimeMillis()
                if (update.hasCallbackQuery()) {
                    val text = update.callbackQuery.data ?: ""
                    if (text.startsWith("/"))
                        handleIncomingTextMessage(update.callbackQuery.message, text)
                } else update.message?.let { message ->
                    if (message.hasText()) handleIncomingTextMessage(message, message.text)
                }
                val finishTime = System.currentTimeMillis() - millisStarted
                logRequest.info("Processing of update [{}] ended with – {} ms", update.updateId, finishTime)
            }
        }
    }

    val handlers = listOf(
        object : TextHandle("start") {
            suspend override fun execute(message: Message, user: User, payload: String) {
                var postText: String? = null
                if (payload.isNotEmpty()) postText = handleRegisterApprove(user, payload)
                markdown(message) { handleWelcome(user, message.from) }
                if (postText != null) markdown(message) { postText as String }
            }
        },
        object : TextHandle("help") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleHelp(user)
            }
        },

        object : TextHandle("leagues") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleLeagues()
            }
        },
        object : TextHandle("scores") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleScores(user, payload, this.replyCallbacks(it))
            }
        },
        object : TextHandle("day") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleMatchDay(user, payload)
            }
        },
        object : TextHandle("reg") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleRegisterApprove(user, payload)
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
            suspend override fun execute(message: Message, user: User, payload: String) {
                handleRegisterInvite(user, botUsername, payload).forEach { text -> markdown(message) { text } }
            }
        },
        object : TextHandle("addleague") {
            suspend override fun execute(message: Message, user: User, payload: String) = markdown(message) {
                handleNewLeague(user, payload)
            }
        }
    )

    suspend fun handleIncomingTextMessage(message: Message, original: String) {
        try {
            val id = message.from.id
            val text = original.trim()
            val user = get(id) ?: User(id, message.chatId)
            val handle = handlers.find { it.isDo(text) } ?: return
            log.info("New action [{}] found from {}", handle.name, user.id)
            handle.run(message, user, text)
        } catch (e: Exception) {
            log.error("Unexpected message error", e)
            markdown(message) { label("unexpected-error") }
        }
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
