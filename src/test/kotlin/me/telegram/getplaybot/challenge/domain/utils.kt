package me.telegram.getplaybot.challenge.domain

import me.telegram.getplaybot.challenge.domain.game.Predict
import me.telegram.getplaybot.challenge.domain.game.Round
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.domain.sport.Match
import me.telegram.getplaybot.challenge.domain.sport.Team

fun round(predicts: List<Predict>) = Round("1", User(1, null), User(2, null), predicts)

fun match(result: MatchResult? = null) =
    Match("1", Team("1", "team 1"), Team("2", "team 2"), result)

fun predict(home: MatchResult? = null, away: MatchResult? = null, result: MatchResult? = null) =
    Predict(match(result), home, away)
