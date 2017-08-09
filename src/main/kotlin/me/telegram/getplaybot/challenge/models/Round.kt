package me.telegram.getplaybot.challenge.models

typealias RoundResult = Pair<Int, Int>

data class Round(
    val id: String,
    val home: User,
    val away: User,
    val predicts: List<Predict>
) {
    fun isComplete() = predicts.all { it.result != null }

    fun compute(): Pair<RoundResult, RoundResult> {
        val (home, away) = predicts.fold(Pair(0, 0)) { acc, predict ->
            acc.let {
                val scores = predict.compute()
                acc.copy(scores.first, scores.second)
            }
        }

        val (pointsHome, pointsAway) = when (home.compareTo(away)) {
            1 -> Pair(3, 0)
            -1 -> Pair(0, 3)
            else -> Pair(1, 1)
        }
        
        return Pair(
            RoundResult(pointsHome, home),
            RoundResult(pointsAway, away)
        )
    }
}
