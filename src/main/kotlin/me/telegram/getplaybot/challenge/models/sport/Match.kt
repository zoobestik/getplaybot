package me.telegram.getplaybot.challenge.models.sport

import me.telegram.getplaybot.challenge.models.MatchResult

data class Match(
    val id: String,
    val home: Team,
    val away: Team,
    val result: MatchResult?
)
