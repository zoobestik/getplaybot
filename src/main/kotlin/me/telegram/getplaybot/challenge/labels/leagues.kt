package me.telegram.getplaybot.challenge.labels.leagues

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "list-no-items" to "Нет пока не одного соревнования :(",

    "create-id-required" to "Не получилось найти `ID`. Этой командой можно создать новую лигу " +
        "или обновить настройки для уже созданной. Попробуй:\n" +
        "`/addleague ID [SCHEME] [SCHEME_ARGS] NAME...`",
    "create-type-required" to "Для создания лиги необходимо указать `SCHEME`!",
    "create-type-illegal" to "Не смог найти подходящую схему! :(",
    "create-name-required" to "Не получилось найти `NAME`. Название обязательно!",
    "create-year-required" to "Для этой схемы необходимо указать год!",
    "create-year-invalid" to "Год не похож на год! :("
)

fun textLeagueCreated(name: String) = "Лига *$name* готова!"

fun label(key: String) = label(list, key)
