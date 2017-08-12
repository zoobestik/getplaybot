package me.telegram.getplaybot.challenge.i18n.leagues

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "list-no-items" to "Нет пока не одного соревнования :(",

    "create-id-required" to "Не получилось найти `ID`. Это командой можно создать новую лигу " +
        "или обновить имя текущей. Попробуй:\n" +
        "`/addleague ID [SCHEME] [SCHEME_ARGS] NAME...`",
    "create-type-required" to "Для создания лиги необходимо указать `SCHEME`!",
    "create-type-illegal" to "Не смог найти подходящую схему! :(",
    "create-name-required" to "Не получилось найти `NAME`. Название обязательно!",
    "create-year-required" to "Для этой схемы необходимо указать год!",
    "create-year-invalid" to "Год не похож на год! :("
)

fun textLeagueCreated(name: String) = "Лига *$name* готова!"

fun i18n(key: String) = i18n(list, key)
