package me.telegram.getplaybot.handles

import me.telegram.getplaybot.lib.i18n.scores
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.leagues.get

suspend fun handleScores(): String {
    val league = get("champions-2017") ?:
            return scores.getOrDefault("no-league-found", "no-league-found")

    return league.teams
            .map { "${it.userId} — ${it.id}" }
            .joinToString("\n")
}

suspend fun handleMatchDay(): String {
    return "matchday" // @ToDo: список последнего дня
}

suspend fun handleMe(user: User): String {
    return "me" // @ToDo: информация о команде
}
