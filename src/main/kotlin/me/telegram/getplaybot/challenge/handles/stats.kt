package me.telegram.getplaybot.challenge.handles.stats

import me.telegram.getplaybot.challenge.i18n.scores.i18n
import me.telegram.getplaybot.challenge.models.game.User
import me.telegram.getplaybot.challenge.services.leagues.get

suspend fun handleScores(): String {
    val league = get("champions-2017") ?: return i18n("scores-no-league")
    if (league.teams.isEmpty()) return i18n("scores-no-teams")
    return league.teams.map { "${it.userId} — ${it.id}" }.joinToString("\n")
}

suspend fun handleMatchDay(): String {
    val league = get("champions-2017") ?: return i18n("matchday-no-league")
//    val last = lastMatch(league.id) ?: return i18n("matchday-no-last")
    return league.id
}

suspend fun handleMe(user: User): String {
    return "me" // @ToDo: информация о команде
}
