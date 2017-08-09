package me.telegram.getplaybot.challenge.models

class PredictNotComplete : Exception()

fun defaultPredict() = MatchResult(0, 0)

data class Predict(
    val home: MatchResult?,
    val away: MatchResult?,
    val result: MatchResult?
) {
    val computedHome
        get() = home ?: defaultPredict()

    val computedAway
        get() = away ?: defaultPredict()

    fun compute(): Pair<Int, Int> {
        val result = result ?: throw PredictNotComplete()
        return Pair(computedHome.scores(result), computedAway.scores(result))
    }
}
