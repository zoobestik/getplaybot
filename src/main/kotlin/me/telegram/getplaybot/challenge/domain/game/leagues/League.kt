package me.telegram.getplaybot.challenge.domain.game.leagues

import me.telegram.getplaybot.challenge.domain.game.Team

interface LeagueFactory<T : League> {
    fun isType(league: T): Boolean
    suspend fun fromString(str: String): T
}

interface League {
    val id: String
    val name: String
    val teams: List<Team>

    operator fun component1(): String
    operator fun component2(): String

    fun copy(id: String = this.id, name: String = this.name, teams: List<Team> = this.teams): League

    companion object Factory : LeagueFactory<League> {
        override fun isType(league: League): Boolean {
            throw Exception("Not implemented")
        }

        suspend override fun fromString(str: String): League {
            throw Exception("Not implemented")
        }
    }
}
