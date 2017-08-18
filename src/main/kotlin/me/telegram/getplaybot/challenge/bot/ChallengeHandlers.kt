package me.telegram.getplaybot.challenge.bot

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.label
import me.telegram.getplaybot.lib.TelegramUser
import me.telegram.getplaybot.lib.getEnv
import me.telegram.getplaybot.lib.logger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.api.methods.BotApiMethod
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updateshandlers.SentCallback
import java.io.Serializable
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.system.measureTimeMillis
import me.telegram.getplaybot.challenge.services.users.get as findUser

class ChallengeHandlers : TelegramLongPollingBot() {
    companion object {
        val log by logger()
        val logRequest: Logger = LoggerFactory.getLogger("ChallengeHandlersRequests")
    }

    override fun getBotToken(): String = getEnv("BOT_CHALLENGE_TOKEN")
    override fun getBotUsername(): String = getEnv("BOT_CHALLENGE_NAME")

    val link get() = "https://t.me/$botUsername"
    val handlers = handlers(this)

    override fun onUpdateReceived(update: Update?) {
        logRequest.info("New update received with {}", update)
        launch(CommonPool) {
            try {
                if (update != null) {
                    logRequest.info("Processing of update [{}] ended with – {} ms", update.updateId, measureTimeMillis {
                        when {
                            update.hasCallbackQuery() -> {
                                val query = update.callbackQuery
                                val text = query.data ?: ""
                                if (text.startsWith("/"))
                                    handleIncomingTextMessage(query.message, text, query.from)
                            }

                            update.hasMessage() -> {
                                val message = update.message
                                if (message.hasText()) handleIncomingTextMessage(message, message.text)
                            }

                            else -> log.warn("No message processor for {}", update.updateId)
                        }
                    })
                }
            } catch (e: Throwable) {
                log.error("Unexpected message error", e)
            }
        }
    }

    private suspend fun handleIncomingTextMessage(message: Message, text: String, fromMessage: TelegramUser? = null) {
        try {
            val handle = handlers.get(text)
            if (handle != null) {
                val msgId = message.messageId
                log.info("New action [{}] found for {}", handle.name, msgId)

                val from = fromMessage ?: message.from
                val userId = from.id
                val user = findUser(userId) ?: User(userId, message.chatId).let {
                    log.info("Unknown user for {}", msgId)
                    it
                }

                handle.run(message, user, text)
            }
        } catch (e: Throwable) {
            markdown(message) { label("unexpected-error") }
            throw e
        }
    }

    private suspend fun <T : Serializable> execute(method: BotApiMethod<T>) = suspendCoroutine<T> { cont ->
        executeAsync(method, object : SentCallback<T> {
            override fun onResult(method: BotApiMethod<T>, response: T) = cont.resume(response)
            override fun onError(method: BotApiMethod<T>, e: TelegramApiRequestException) = cont.resumeWithException(e)
            override fun onException(method: BotApiMethod<T>, e: Exception) = cont.resumeWithException(e)
        })
    }

    private suspend fun image(message: Message, block: MessagePhotoBody) {
        val reply = SendPhoto()
        reply.setChatId(message.chatId)
        block(reply)
        sendPhoto(reply)
    }

    private suspend fun sendText(message: Message, block: MessageBody) {
        val reply = SendMessage()
        reply.setChatId(message.chatId)
        block(reply)
        execute<Message>(reply)
    }

    suspend fun markdown(message: Message, block: MessageTextBody) {
        sendText(message) {
            it.enableMarkdown(true)
            it.text = block(it)
        }
    }

    suspend fun <T> markdown(message: Message, iterator: Iterator<T>, body: suspend (T, SendMessage) -> String) {
        while (iterator.hasNext()) {
            markdown(message) {
                body(iterator.next(), it)
            }
        }
    }
}

