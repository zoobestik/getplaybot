package me.telegram.getplaybot.challenge.models.game

import me.telegram.getplaybot.lib.IntWinner
import me.telegram.getplaybot.lib.IntWinner.AWAY
import me.telegram.getplaybot.lib.IntWinner.HOME

typealias RoundTeamResult = Pair<Int, Int>
data class RoundResult(val home: RoundTeamResult, val away: RoundTeamResult, val matches: List<PredictPoints>)

data class Round(
    val id: String,
    val home: User,
    val away: User,
    val predicts: List<Predict>
) {
    val complete
        get() = predicts.all { it.complete }

    fun compute(): RoundResult {
        val matches = predicts.map { it.compute() }
        val (home, away) = matches.fold(PredictPoints(0, 0)) { acc, (home, away) ->
            acc.copy(home, away)
        }

        val (pointsHome, pointsAway) = when (IntWinner.valueOf(home.compareTo(away))) {
            HOME -> Pair(3, 0)
            AWAY -> Pair(0, 3)
            else -> Pair(1, 1)
        }

        return RoundResult(
            RoundTeamResult(pointsHome, home),
            RoundTeamResult(pointsAway, away),
            matches
        )
    }
}
