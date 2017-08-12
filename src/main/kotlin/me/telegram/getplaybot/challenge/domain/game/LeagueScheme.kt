package me.telegram.getplaybot.challenge.domain.game

import me.telegram.getplaybot.lib.pairArgs

class LeagueSchemeNameRequired : Exception()
class LeagueSchemeYearInvalid : Exception()
class LeagueSchemeChampionsYearRequired : Exception()

enum class LeagueScheme {
    CHAMPIONS {
        override fun isType(league: League): Boolean = league is UEFAChampionsLeague

        fun args(str: String): Pair<String, String> {
            val args = pairArgs(str)
            val (year) = args
            if (year.isNotEmpty() && !year.matches("^20\\d{2}$".toRegex()))
                throw LeagueSchemeYearInvalid()
            return args
        }

        override fun create(id: String, str: String): League {
            val (year, name) = args(str)
            if (year.isEmpty()) throw LeagueSchemeChampionsYearRequired()
            if (name.isEmpty()) throw LeagueSchemeNameRequired()
            return UEFAChampionsLeague(id, name, year)
        }

        override fun merge(league: League, str: String): League = when (league) {
            is UEFAChampionsLeague -> {
                val (year, name) = args(str)
                league.copy(name = name, year = year)
            }
        }
    };

    abstract fun isType(league: League): Boolean

    abstract fun create(id: String, str: String): League
    abstract fun merge(league: League, str: String): League

    companion object {
        fun from(findValue: League): LeagueScheme = values().first {
            it.isType(findValue)
        }
    }
}
