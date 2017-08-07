package me.telegram.getplaybot.challenge.models

data class Match(
    val id: String,
    val home: FootballTeam,
    val away: FootballTeam,
    val result: MatchResult?
)
