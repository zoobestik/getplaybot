package me.telegram.getplaybot.lib

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

typealias ReplyCallbackButton = Pair<String, String>
typealias ReplyCallbackMarkup = List<List<ReplyCallbackButton>>
typealias ReplyWithCallbacks = (message: SendMessage) -> ReplyCallbackMarkup
typealias BindReplyWithCallbacks = (ReplyWithCallbacks) -> Unit

fun replyWithCallbacks(message: SendMessage, key: String): BindReplyWithCallbacks =
    fun(block: ReplyWithCallbacks) = replyWithCallbacks(message, key, block)

fun replyWithCallbacks(message: SendMessage, key: String, block: ReplyWithCallbacks) {
    val inlineKeyboardMarkup = InlineKeyboardMarkup()

    inlineKeyboardMarkup.keyboard = block(message).map {
        it.map { (text, data) ->
            val button = InlineKeyboardButton()
            button.text = text
            button.callbackData = "/$key $data"
            button
        }
    }

    message.replyMarkup = inlineKeyboardMarkup
}

fun <T, R> telegramTwoColumns(list: List<T>, block: (T) -> R): List<List<R>> = groupByIndex(2, list, block)

fun <T, R> groupByIndex(size: Int, list: List<T>, block: (T) -> R): List<List<R>> =
    list.withIndex()
        .groupBy({ it.index / size }, { block(it.value) })
        .values.toList()
