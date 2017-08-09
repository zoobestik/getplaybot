package me.telegram.getplaybot.challenge.models.game

import me.telegram.getplaybot.challenge.models.MatchResult
import me.telegram.getplaybot.challenge.models.sport.Match

class PredictNotComplete : Exception()

fun defaultPredict() = MatchResult(0, 0)

data class Predict(
    val match: Match,
    val home: MatchResult? = null,
    val away: MatchResult? = null
) {
    val complete
        get() = match.result != null

    val computedHome
        get() = home ?: defaultPredict()

    val computedAway
        get() = away ?: defaultPredict()

    fun compute(): Pair<Int, Int> {
        val result = match.result ?: throw PredictNotComplete()
        return Pair(computedHome.scores(result), computedAway.scores(result))
    }
}
