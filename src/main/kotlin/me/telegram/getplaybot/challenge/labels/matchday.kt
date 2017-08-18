package me.telegram.getplaybot.challenge.labels.matchday

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "day-no-league" to "Нет соревнования — нет матчей :("
)

fun label(key: String) = label(list, key)
