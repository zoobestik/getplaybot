package me.telegram.getplaybot.challenge.labels.league

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "leagues-no-items" to "Нет пока ниодного соревнования :(",
    "league-no-leagues" to "Нет активных сорвенований",
    "league-no-league" to "Нет соревнования – нет таблицы :(",
    "league-no-teams" to "Здесь пока никого нет :(",
    "league-help-choose" to "Есть несколько активных турниров. Можно выбрать из списка, или отправить комманду: "
        + "`/league LEAGUE_ID`",

    "league-create-id-required" to "Не получилось найти `ID`. Этой командой можно создать новую лигу " +
        "или обновить настройки для уже созданной. Попробуй:\n" +
        "`/addleague ID [SCHEME] [SCHEME_ARGS] NAME...`",
    "league-create-type-required" to "Для создания лиги необходимо указать `SCHEME`!",
    "league-create-type-illegal" to "Не смог найти подходящую схему! :(",
    "league-create-name-required" to "Не получилось найти `NAME`. Название обязательно!",
    "league-create-year-required" to "Для этой схемы необходимо указать год!",
    "league-create-year-invalid" to "Год не похож на год! :("
)

fun textLeagueCreated(name: String) = "Лига *$name* готова!"

fun label(key: String) = label(list, key)
