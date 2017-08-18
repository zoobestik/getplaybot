package me.telegram.getplaybot.challenge.labels.handles

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "league" to "посмтреть таблицу результатов",
    "day" to "последние результаты",
    "vote" to "дать прогноз на тур",
    "help" to "полный список комманд, доступный вам",
    "reg" to "принять приглашение для участия в турнире",
    "invite" to "получить инвайт–ссылку для друга",
    "leagues" to "посмотреть список турниров",
    "addleague" to "создать новую лигу или обновить имя текущую"
)

fun label(key: String) = label(list, key)
