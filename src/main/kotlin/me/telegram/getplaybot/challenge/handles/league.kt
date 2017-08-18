package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.domain.game.leagues.LeagueScheme
import me.telegram.getplaybot.challenge.domain.game.leagues.LeagueSchemeChampionsYearRequired
import me.telegram.getplaybot.challenge.domain.game.leagues.LeagueSchemeNameRequired
import me.telegram.getplaybot.challenge.domain.game.leagues.LeagueSchemeYearInvalid
import me.telegram.getplaybot.challenge.labels.league.label
import me.telegram.getplaybot.challenge.labels.league.textLeagueCreated
import me.telegram.getplaybot.challenge.services.leagues.createOrUpdate
import me.telegram.getplaybot.challenge.services.leagues.get
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.challenge.services.leagues.listOfActive
import me.telegram.getplaybot.lib.BindReplyWithCallbacks
import me.telegram.getplaybot.lib.ReplyCallbackButton
import me.telegram.getplaybot.lib.pairArgs
import me.telegram.getplaybot.lib.telegramTwoColumns

suspend fun handleLeague(user: User, inputId: String, reply: BindReplyWithCallbacks): String {
    val leagueId = when {
        inputId.isNotEmpty() -> inputId
        user.defaultLeagueId != null -> user.defaultLeagueId
        else -> {
            val list = listOfActive()
            when {
                list.isEmpty() -> return label("league-no-leagues")
                list.size == 1 -> list[0].id
                else -> {
                    reply({
                        telegramTwoColumns(list) { (name, id) ->
                            ReplyCallbackButton(name, id)
                        }
                    })
                    return label("league-help-choose")
                }
            }
        }
    }

    val league = get(leagueId) ?: return label("league-no-league")
    if (league.teams.isEmpty()) return label("league-no-teams")

    return league.teams.mapIndexed { index, team -> "$index. ${team.name}" }.joinToString("\n")
}

suspend fun handleLeagues(): String = list().let {
    if (it.isEmpty()) label("leagues-no-items") else
        it
            .mapIndexed { index, (id, name) -> "${index + 1}. *$name* [$id]" }
            .joinToString("\n")
}

suspend fun handleCreateLeague(payload: String): String {
    try {
        val league = try {
            val (type, args) = pairArgs(payload)
            if (type.isEmpty()) return label("league-create-type-required")
            LeagueScheme.valueOf(type.toUpperCase()).factory.fromString(args)
        } catch (e: IllegalArgumentException) {
            return label("league-create-type-illegal")
        }

        createOrUpdate(league)

        return textLeagueCreated(league.name)
    } catch (e: LeagueSchemeNameRequired) {
        return label("league-create-name-required")
    } catch (e: LeagueSchemeYearInvalid) {
        return label("league-create-year-invalid")
    } catch (e: LeagueSchemeChampionsYearRequired) {
        return label("league-create-year-required")
    }
}
