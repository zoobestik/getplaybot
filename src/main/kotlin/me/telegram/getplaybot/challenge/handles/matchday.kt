package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.matchday.label
import me.telegram.getplaybot.challenge.services.leagues.get

suspend fun handleVote(user: User): String {
    throw Exception("Not implemented")
}

suspend fun handleDay(user: User, lid: String): String {
    val leagueId = user.defaultLeagueId ?: lid
    val league = get(leagueId) ?: return label("day-no-league")
    return league.id
}
