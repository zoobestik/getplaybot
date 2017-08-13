package me.telegram.getplaybot.challenge.services.team

import me.telegram.getplaybot.challenge.domain.game.Team
import me.telegram.getplaybot.challenge.domain.game.User
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("TeamsServices")

class TeamNotFound : Exception()

private val teams: MutableMap<String, Team> = mutableMapOf()

suspend fun get(id: String): Team? = teams[id]

suspend fun new(user: User?): Team {
    val team = Team(teams.size.toString(), user)
    teams[team.id] = team
    log.info("Team created {}", team)
    return team
}
