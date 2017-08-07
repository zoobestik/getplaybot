package me.telegram.getplaybot.challenge.i18n.scores

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "scores-no-league" to "Нет соревнования – нет таблицы :(",
    "scores-no-teams" to "Здесь пока никого нет :(",
    "matchday-no-league" to "Нет соревнования — нет матчей :(",
    "matchday-no-last" to "Еще небыло результатов"
)

fun i18n(key: String) = i18n(list, key)
