package me.telegram.getplaybot.challenge.services.leagues

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.runBlocking
import me.telegram.getplaybot.challenge.domain.MatchResult
import me.telegram.getplaybot.challenge.domain.game.Team
import me.telegram.getplaybot.challenge.domain.game.leagues.League
import me.telegram.getplaybot.challenge.domain.game.leagues.UEFAChampLeague
import me.telegram.getplaybot.challenge.domain.sport.Match
import me.telegram.getplaybot.challenge.domain.sport.uefa.champleague.UEFARound
import me.telegram.getplaybot.challenge.services.team.TeamNotFound
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import me.telegram.getplaybot.challenge.domain.sport.Team as SportTeam
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

    leagues[league.id] = league.copy(
        teams = league.teams + team
    )

    log.info("Team approved for {} — {}", league.id, team)
}

val requests = newFixedThreadPoolContext(10, "roundRequests")

suspend fun getRounds(league: UEFAChampLeague): List<UEFARound> {
    val url = "http://www.uefa.com/uefachampionsleague/season=${league.year}/matches/"

    val rounds = Jsoup.parse(khttp.get(url).raw, "utf-8", url)
        .select("a[data-round], a[data-match-day]")
        .map({ node ->
            if (node.hasAttr("data-match-day"))
                Pair("matchDayId", node.attr("data-match-day"))
            else
                Pair("roundId", node.attr("data-round"))
        })
        .distinct()
        .map({ (param, value) ->
            Pair(value, async(requests) {
                khttp
                    .get("http://digital-api.uefa.com/v1/matches/versions?competitionId=1&seasonYear=2018&$param=$value")
                    .jsonObject
            })
        })

    return runBlocking {
        rounds.map { (id, request) ->
            UEFARound(id, request.await().keySet().map {
                val data = khttp.get("http://digital-api.uefa.com/v2/matches/$it?language=RU").jsonObject
                val home = data.getJSONObject("homeTeam")
                val away = data.getJSONObject("awayTeam")
                val result: MatchResult?

                try {
                    result = MatchResult(
                        data.getJSONObject("homeTeamScore").getInt("score"),
                        data.getJSONObject("awayTeamScore").getInt("score")
                    )
                } catch (e: Exception) {
                    throw Exception("Match not parsed")
                }

                Match(data.getString("id"),
                    SportTeam(home.getString("id"), home.getString("displayName")),
                    SportTeam(away.getString("id"), away.getString("displayName")),
                    result
                )
            })
        }
    }
}
