package me.telegram.getplaybot.challenge.domain.sport

import me.telegram.getplaybot.challenge.domain.MatchResult

data class Match(
    val id: String,
    val home: Team,
    val away: Team,
    val result: MatchResult?
)
