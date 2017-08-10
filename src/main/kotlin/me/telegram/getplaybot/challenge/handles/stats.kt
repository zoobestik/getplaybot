package me.telegram.getplaybot.challenge.handles.stats

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.i18n.scores.i18n
import me.telegram.getplaybot.challenge.services.leagues.get

suspend fun handleScores(user: User, lid: String): String {
    val leagueId = user.defaultLeagueId ?: lid
    val league = get(leagueId) ?: return i18n("scores-no-league")
    if (league.teams.isEmpty()) return i18n("scores-no-teams")
    return league.teams.mapIndexed { index, team -> "$index. ${team.name}" }.joinToString("\n")
}

suspend fun handleMatchDay(user: User, lid: String): String {
    val leagueId = user.defaultLeagueId ?: lid
    val league = get(leagueId) ?: return i18n("matchday-no-league")
    return league.id
}

suspend fun handleMe(user: User): String {
    return "me" // @ToDo: информация о команде
}
