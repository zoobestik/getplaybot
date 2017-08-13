package me.telegram.getplaybot.challenge.services.leagues

import me.telegram.getplaybot.challenge.domain.game.League
import me.telegram.getplaybot.challenge.domain.game.Team
import me.telegram.getplaybot.challenge.domain.game.UEFAChampionsLeague
import me.telegram.getplaybot.challenge.services.team.TeamNotFound
import org.slf4j.LoggerFactory
import me.telegram.getplaybot.challenge.services.team.get as team
import me.telegram.getplaybot.challenge.services.users.get as user

class LeagueNotFound : Exception()

private val log = LoggerFactory.getLogger("LeaguesServices")

private val leagues = mutableMapOf<String, League>()

suspend fun get(id: String): League? = leagues[id]

suspend fun list(): List<League> = leagues.values.toList()
suspend fun listOfActive(): List<League> = leagues.values.toList()

suspend fun createOrUpdate(league: League): League {
    leagues.put(league.id, league)
    log.info("League updated for {}", league)
    return league
}

suspend fun addTeam(leagueId: String, teamId: String) {
    val team = team(teamId) ?: throw TeamNotFound()
    return addTeam(leagueId, team)
}

suspend fun addTeam(leagueId: String, team: Team) {
    val league = get(leagueId) ?: throw LeagueNotFound()
    league.teams.add(team)
    log.info("Team approved for {} — {}", league.id, team)
}

suspend fun loadRound(league: UEFAChampionsLeague) {

}
