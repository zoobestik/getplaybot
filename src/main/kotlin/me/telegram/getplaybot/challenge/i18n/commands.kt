package me.telegram.getplaybot.challenge.i18n.commands

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "scores" to "посмтреть таблицу результатов",
    "day" to "последние результаты",
    "vote" to "дать прогноз на тур",
    "help" to "полный список комманд, доступный вам",
    "reg" to "принять приглашение для участия в турнире",
    "invite" to "получить инвайт–ссылку для друга",
    "leagues" to "посмотреть список турниров",
    "addleague" to "создать новую лигу или обновить имя текущую"
)

fun i18n(key: String) = i18n(list, key)
