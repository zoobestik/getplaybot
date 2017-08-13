package me.telegram.getplaybot.challenge.handles.league

import me.telegram.getplaybot.challenge.domain.game.*
import me.telegram.getplaybot.challenge.labels.leagues.label
import me.telegram.getplaybot.challenge.labels.leagues.textLeagueCreated
import me.telegram.getplaybot.challenge.services.leagues.createOrUpdate
import me.telegram.getplaybot.challenge.services.leagues.get
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.lib.pairArgs

suspend fun handleLeagues(): String = list().let {
    if (it.isEmpty()) label("list-no-items") else
        it
            .mapIndexed { index, (id, name) -> "${index + 1}. *$name* [$id]" }
            .joinToString("\n")
}

suspend fun handleNewLeague(user: User, payload: String): String {
    try {
        val (id, str) = pairArgs(payload)
        if (id.isEmpty()) return label("create-id-required")

        val league = get(id).let {
            try {
                if (it == null) {
                    val (type, args) = pairArgs(str)
                    if (type.isEmpty()) return label("create-type-required")
                    LeagueScheme.valueOf(type.toUpperCase()).create(id, args)
                } else {
                    LeagueScheme.from(it).merge(it, str)
                }
            } catch (e: IllegalArgumentException) {
                return label("create-type-illegal")
            }
        }

        createOrUpdate(league)

        return textLeagueCreated(league.name)
    } catch (e: LeagueSchemeNameRequired) {
        return label("create-name-required")
    } catch (e: LeagueSchemeYearInvalid) {
        return label("create-year-invalid")
    } catch (e: LeagueSchemeChampionsYearRequired) {
        return label("create-year-required")
    }
}
