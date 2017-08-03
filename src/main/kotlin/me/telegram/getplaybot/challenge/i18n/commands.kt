package me.telegram.getplaybot.challenge.i18n.commands

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "scores" to "посмтреть таблицу результатов",
    "invite" to "получить инвайт–ссылку для друга",
    "reg" to "принять приглашение для участия в турнире"
)

fun i18n(key: String) = i18n(list, key)
