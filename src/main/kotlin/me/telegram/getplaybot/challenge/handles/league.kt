package me.telegram.getplaybot.challenge.handles.league

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.i18n.leagues.i18n
import me.telegram.getplaybot.challenge.i18n.leagues.textLeagueCreated
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.challenge.services.leagues.createOrUpdate as createLeague

suspend fun handleLeagues(): String {
    val list = list()
    return if (list.isEmpty()) i18n("leagues-no-anyone") else
        list
            .mapIndexed { index, (id, name) -> "${index + 1}. $name ($id)" }
            .joinToString("\n")
}

suspend fun handleNewLeague(user: User, payload: String): String {
    val splitted = payload.split(" ", limit = 2)
    if (splitted.size < 2 || splitted.any { it.isEmpty() })
        return i18n("addleagues-bad-params")

    return textLeagueCreated(createLeague(splitted[0], splitted[1]).name)
}
