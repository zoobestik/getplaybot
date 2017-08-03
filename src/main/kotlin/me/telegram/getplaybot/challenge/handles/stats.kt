package me.telegram.getplaybot.challenge.handles.stats

import me.telegram.getplaybot.challenge.i18n.scores.i18n
import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.services.leagues.get

suspend fun handleScores(): String {
    val league = get("champions-2017") ?: return i18n("league-not-found")
    return league.teams.map { "${it.userId} — ${it.id}" }.joinToString("\n")
}

suspend fun handleMatchDay(): String {
    return "matchday" // @ToDo: список последнего дня
}

suspend fun handleMe(user: User): String {
    return "me" // @ToDo: информация о команде
}
