package me.telegram.getplaybot.challenge.domain.game

data class League(
    val id: String,
    val teams: MutableList<Team> = mutableListOf()
)
