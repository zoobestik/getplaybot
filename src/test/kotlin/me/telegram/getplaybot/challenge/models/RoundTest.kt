package me.telegram.getplaybot.challenge.models

import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class RoundTest {
    @Test
    @Ignore
    fun isComplete() {
    }
    
    fun round(predicts: List<Predict>) = Round("1", User(1, null), User(2, null), predicts)

    @Test
    fun computeLoose() {
        val predicts = listOf(Predict(
            MatchResult(1, 0),
            MatchResult(3, 3),
            MatchResult(4, 6)
        ))

        val ( home, away ) = round(predicts).compute()

        assertEquals(0, home.first)
        assertEquals(3, away.first)
        assertTrue(home.second < away.second)
    }

    @Test
    fun computeTied() {
        val predicts = listOf(
            Predict(MatchResult(1, 0), MatchResult(3, 0), MatchResult(2, 0)),
            Predict(MatchResult(2, 2), MatchResult(4, 4), MatchResult(3, 3))
        )

        val ( home, away ) = round(predicts).compute()

        assertEquals(1, home.first)
        assertEquals(1, away.first)
        assertEquals(home.second, away.second)
    }

    @Test
    fun computeWin() {
        val predicts = listOf(
            Predict(MatchResult(4, 0), MatchResult(6, 3), MatchResult(4, 3)),
            Predict(MatchResult(1, 1), MatchResult(0, 0), MatchResult(2, 2))
        )

        val ( home, away ) = round(predicts).compute()

        assertEquals(3, home.first)
        assertEquals(0, away.first)
        assertTrue(home.second > away.second)
    }
}
