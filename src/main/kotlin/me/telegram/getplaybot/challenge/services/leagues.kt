package me.telegram.getplaybot.challenge.services.leagues

import me.telegram.getplaybot.challenge.models.League
import me.telegram.getplaybot.challenge.models.Team
import me.telegram.getplaybot.challenge.models.User

class TeamAlreadyIn : Exception()

private val leagues = mapOf(
    "champions-2017" to League("champions-2017")
)

private val teams: MutableList<Team> = mutableListOf()

class LeagueNotFound : Exception()

suspend fun get(id: String): League? = leagues[id]

suspend fun add(leagueId: String, user: User) {
    val userId = user.id;
    val league = get(leagueId) ?: throw LeagueNotFound()
    if (league.teams.any { userId == it.userId }) throw TeamAlreadyIn()

    val team = Team(teams.size.toString(), user.id)
    teams.add(team)
    league.teams.add(team)
}
