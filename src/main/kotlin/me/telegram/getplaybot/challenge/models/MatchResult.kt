package me.telegram.getplaybot.challenge.models

const val GOALS_POINTS_MAX = 5
const val GOALS_POINTS_LOOSE = GOALS_POINTS_MAX + 2
const val GOALS_POINTS_WINNER = GOALS_POINTS_LOOSE + 3
const val POINTS_WINNER = GOALS_POINTS_WINNER + GOALS_POINTS_LOOSE + 3
const val POINTS_SUCCESS = POINTS_WINNER + GOALS_POINTS_WINNER + GOALS_POINTS_MAX + 5

fun isWinner(winner: Int, index: Int) = when (winner) {
    1 -> index == 0
    0 -> true
    -1 -> index == 1
    else -> false
}

data class MatchResult(val home: Int, val away: Int) {
    val winner
        get() = home.compareTo(away)

    fun scores(other: MatchResult): Int {
        if (other == this)
            return POINTS_SUCCESS

        var score = 0
        val winner = other.winner
        if (winner == this.winner) score += POINTS_WINNER

        val result = listOf(
            Pair(this.home, other.home),
            Pair(this.away, other.away)
        )

        var goalsMaxPoints = GOALS_POINTS_MAX

        val goals = result.foldIndexed(0) { index, acc, (current, other) ->
            acc + when (current) {
                other ->  if (isWinner(winner, index)) GOALS_POINTS_WINNER else GOALS_POINTS_LOOSE
                else -> {
                    val min = listOf(current, other, goalsMaxPoints).min() ?: 0
                    goalsMaxPoints -= min
                    min
                }
            }
        }

        return score + goals
    }
}
