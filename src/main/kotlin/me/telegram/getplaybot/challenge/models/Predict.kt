package me.telegram.getplaybot.challenge.models

class PredictNotComplete : Exception()

fun defaultPredict() = MatchResult(0, 0)
fun points(s1: Int, s2: Int): Int = if (s1.compareTo(s2) == 1) 3 else 0

data class Predict(
    val homePredict: MatchResult?,
    val awayPredict: MatchResult?,
    val result: MatchResult?
) {
    val home
        get() = homePredict ?: defaultPredict()

    val away
        get() = awayPredict ?: defaultPredict()

    fun calculate(): Pair<RoundResult, RoundResult> {
        val result = result ?: throw PredictNotComplete()
        val s1 = home.scores(result)
        val s2 = away.scores(result)
        return Pair(
            RoundResult(s1, points(s1, s2)),
            RoundResult(s2, points(s2, s1))
        )
    }
}
