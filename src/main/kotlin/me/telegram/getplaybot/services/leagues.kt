package me.telegram.getplaybot.services.leagues

import me.telegram.getplaybot.models.League
import me.telegram.getplaybot.models.Team
import me.telegram.getplaybot.models.User

private val leagues = mapOf(
        "champions-2017" to League("champions-2017")
)

private val teams: MutableList<Team> = mutableListOf()

suspend fun addTeam(leagueId: String, userId: Int): Team {
    val team = Team(teams.size.toString(), leagueId, userId)
    teams.add(team)
    return team
}

class LeagueNotFound : Exception()

suspend fun get(id: String): League? = leagues[id]

suspend fun add(leagueId: String, user: User) {
    val league = get(leagueId) ?: throw LeagueNotFound()
    league.teams.add(addTeam(leagueId, user.id))
}