package me.telegram.getplaybot.challenge.models

import me.telegram.getplaybot.challenge.models.ResultWinner.*

const val GOALS_POINTS_MAX = 3
const val GOALS_POINTS_LOOSE = GOALS_POINTS_MAX + 2
const val GOALS_POINTS_WINNER = GOALS_POINTS_LOOSE + 1
const val POINTS_WINNER = GOALS_POINTS_WINNER + GOALS_POINTS_LOOSE + 1
const val POINTS_SUCCESS = POINTS_WINNER + GOALS_POINTS_WINNER + GOALS_POINTS_MAX + 4

enum class ResultWinner(val value: Int) {
    HOME(1), DRAW(0), AWAY(-1), ;

    companion object {
        fun valueOf(findValue: Int): ResultWinner = values().first { it.value == findValue }
    }
}

data class MatchResult(val home: Int, val away: Int) {
    val winner
        get() = ResultWinner.valueOf(home.compareTo(away))

    fun scores(real: MatchResult): Int {
        if (real == this)
            return POINTS_SUCCESS

        var score = 0
        val winner = real.winner

        val result = listOf(
            Pair(this.home, real.home),
            Pair(this.away, real.away)
        )

        var goals = result.foldIndexed(0) { index, acc, (current, result) ->
            acc + if (current == result) when {
                winner == HOME && index == 0 -> GOALS_POINTS_WINNER
                winner == AWAY && index == 1 -> GOALS_POINTS_WINNER
                winner == DRAW && acc != GOALS_POINTS_WINNER -> GOALS_POINTS_WINNER
                else -> GOALS_POINTS_LOOSE
            }
            else
                GOALS_POINTS_MAX - Math.abs(result - current)
        }

        if (winner == this.winner) {
            score += POINTS_WINNER
            goals = Integer.max(goals, -GOALS_POINTS_MAX)
        }

        return score + goals
    }
}
