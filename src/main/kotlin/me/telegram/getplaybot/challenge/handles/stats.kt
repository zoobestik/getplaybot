package me.telegram.getplaybot.challenge.handles.stats

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.scores.label
import me.telegram.getplaybot.challenge.services.leagues.get
import me.telegram.getplaybot.challenge.services.leagues.listOfActive
import me.telegram.getplaybot.lib.BindReplyWithCallbacks
import me.telegram.getplaybot.lib.ReplyCallbackButton
import me.telegram.getplaybot.lib.telegramTwoColumns

suspend fun handleScores(user: User, inputId: String, reply: BindReplyWithCallbacks): String {
    val leagueId = when {
        inputId.isNotEmpty() -> inputId
        user.defaultLeagueId != null -> user.defaultLeagueId
        else -> {
            val list = listOfActive()
            when {
                list.isEmpty() -> return label("scores-no-leagues")
                list.size == 1 -> list[0].id
                else -> {
                    reply({
                        telegramTwoColumns(list) { (name, id) ->
                            ReplyCallbackButton(name, id)
                        }
                    })
                    return label("scores-help-choose")
                }
            }
        }
    }

    val league = get(leagueId) ?: return label("scores-no-league")
    if (league.teams.isEmpty()) return label("scores-no-teams")
    return league.teams.mapIndexed { index, team -> "$index. *${team.name}*" }.joinToString("\n")
}

suspend fun handleMatchDay(user: User, lid: String): String {
    val leagueId = user.defaultLeagueId ?: lid
    val league = get(leagueId) ?: return label("day-no-league")
    return league.id
}

suspend fun handleMe(user: User): String {
    return "me" // @ToDo: информация о команде
}
