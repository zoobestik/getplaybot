package me.telegram.getplaybot.challenge.i18n.leagues

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "leagues-no-anyone" to "Нет пока не одного соревнования :(",
    "addleagues-bad-params" to "Не получилось разобрать комманду. Попробуй:\n" +
        "`/addleague [LEAGUE_ID] LEAGUE_NAME...` — можно создать новую или обновить имя текущей."
)

fun textLeagueCreated(name: String) = "Лига $name готова!"

fun i18n(key: String) = i18n(list, key)
