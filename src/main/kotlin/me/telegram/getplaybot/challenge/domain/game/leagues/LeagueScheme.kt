package me.telegram.getplaybot.challenge.domain.game.leagues

enum class LeagueScheme {
    UEFA_CL {
        override val factory: LeagueFactory<League>
            get() = UEFAChampLeague.Factory
    };

    abstract val factory: LeagueFactory<League>
    fun isType(league: League): Boolean = factory.isType(league)

    companion object {
        fun from(findValue: League): LeagueScheme = values().first {
            it.isType(findValue)
        }
    }
}
