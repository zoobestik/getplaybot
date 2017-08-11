package me.telegram.getplaybot.challenge.i18n.scores

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "scores-no-leagues" to "Нет активных сорвенований",
    "scores-no-league" to "Нет соревнования – нет таблицы :(",
    "scores-no-teams" to "Здесь пока никого нет :(",
    "scores-help-choose" to "Есть несколько активных турниров. Можно выбрать из списка, или отправить комманду: "
        + "`/scores LEAGUE_ID`",
    "day-no-league" to "Нет соревнования — нет матчей :("
)

fun i18n(key: String) = i18n(list, key)
