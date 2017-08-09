package me.telegram.getplaybot.challenge.models.game

data class League(
    val id: String,
    val teams: MutableList<Team> = mutableListOf()
)
