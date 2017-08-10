package me.telegram.getplaybot.challenge.domain.game

data class League(
    val id: String,
    val name: String,
    val teams: MutableList<Team> = mutableListOf()
)
