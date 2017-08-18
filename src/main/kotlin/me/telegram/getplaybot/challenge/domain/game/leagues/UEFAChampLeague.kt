package me.telegram.getplaybot.challenge.domain.game.leagues

import me.telegram.getplaybot.challenge.domain.game.Team
import me.telegram.getplaybot.challenge.domain.sport.uefa.champleague.UEFARound
import me.telegram.getplaybot.lib.pairArgs

class LeagueSchemeNameRequired : Exception()
class LeagueSchemeYearInvalid : Exception()
class LeagueSchemeChampionsYearRequired : Exception()

private fun args(str: String): Pair<String, String> {
    val args = pairArgs(str)
    val (year) = args
    if (year.isNotEmpty() && !year.matches("^20\\d{2}$".toRegex()))
        throw LeagueSchemeYearInvalid()
    return args
}

open class UEFAChampLeagueFactory : LeagueFactory<League> {
    override fun isType(league: League): Boolean = league is UEFAChampLeague

    suspend override fun fromString(str: String): UEFAChampLeague {
        val (year, name) = args(str)
        if (year.isEmpty()) throw LeagueSchemeChampionsYearRequired()
        if (name.isEmpty()) throw LeagueSchemeNameRequired()

        return UEFAChampLeague("uefa-cl-$year", name, year.toInt(), listOf(), listOf())
    }
}

data class UEFAChampLeague(
    override val id: String,
    override val name: String,
    val year: Int,
    override val teams: List<Team>,
    val rounds: List<UEFARound>
) : League {

    override fun copy(id: String, name: String, teams: List<Team>): League =
        this.copy(id = id, name = name, teams = teams)

    companion object Factory : UEFAChampLeagueFactory()
}
