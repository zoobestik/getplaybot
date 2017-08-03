package me.telegram.getplaybot.challenge.i18n.scores

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "no-league-found" to "Нет лиги – нет таблицы :("
)

fun i18n(key: String) = i18n(list, key)
