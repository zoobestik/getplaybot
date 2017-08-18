package me.telegram.getplaybot.challenge.labels.handles

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "league" to "посмтреть таблицу турнира",
    "day" to "посмотреть результат тура",
    "vote" to "дать прогноз на тур",
    "help" to "полный список комманд, доступный вам",
    "invite" to "принять приглашение для участия в турнире",
    "--get-invite" to "получить инвайт–ссылку для друга",
    "--list-leagues" to "посмотреть список турниров",
    "--create-league" to "создать новую лигу или обновить имя текущую"
)

fun label(key: String) = label(list, key)
