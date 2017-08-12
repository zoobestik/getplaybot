package me.telegram.getplaybot.challenge.handles.league

import me.telegram.getplaybot.challenge.domain.game.*
import me.telegram.getplaybot.challenge.i18n.leagues.i18n
import me.telegram.getplaybot.challenge.i18n.leagues.textLeagueCreated
import me.telegram.getplaybot.challenge.services.leagues.createOrUpdate
import me.telegram.getplaybot.challenge.services.leagues.get
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.lib.pairArgs

suspend fun handleLeagues(): String = list().let {
    if (it.isEmpty()) i18n("list-no-items") else
        it
            .mapIndexed { index, (id, name) -> "${index + 1}. *$name* [$id]" }
            .joinToString("\n")
}

suspend fun handleNewLeague(user: User, payload: String): String {
    try {
        val (id, str) = pairArgs(payload)
        if (id.isEmpty()) return i18n("create-id-required")

        val league = get(id).let {
            try {
                if (it == null) {
                    val (type, args) = pairArgs(str)
                    if (type.isEmpty()) return i18n("create-type-required")
                    LeagueScheme.valueOf(type.toUpperCase()).create(id, args)
                } else {
                    LeagueScheme.from(it).merge(it, str)
                }
            } catch (e: IllegalArgumentException) {
                return i18n("create-type-illegal")
            }
        }

        createOrUpdate(league)

        return textLeagueCreated(league.name)
    } catch (e: Exception) {
        return i18n(
            when (e) {
                is LeagueSchemeNameRequired -> "create-name-required"
                is LeagueSchemeYearInvalid -> "create-year-invalid"
                is LeagueSchemeChampionsYearRequired -> "create-year-required"
                else -> throw e
            }
        )
    }
}
