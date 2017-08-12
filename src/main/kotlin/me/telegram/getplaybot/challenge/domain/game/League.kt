package me.telegram.getplaybot.challenge.domain.game

sealed class League {
    abstract val id: String
    abstract val name: String
    open val teams: MutableList<Team> = mutableListOf()

    abstract operator fun component1(): String
    abstract operator fun component2(): String
}

data class UEFAChampionsLeague(
    override val id: String,
    override val name: String,
    val year: String,
    override val teams: MutableList<Team> = mutableListOf()
) : League()
