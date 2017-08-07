package me.telegram.getplaybot.challenge.models

typealias RoundResult = Pair<Int, Int>

data class Round(
    val id: String,
    val home: User,
    val away: User,
    val predicts: List<Predict>
) {
    fun isPadding() = predicts.any { it.result == null }

    fun getScores() {
        predicts.map { it.calculate() }
    }
}
