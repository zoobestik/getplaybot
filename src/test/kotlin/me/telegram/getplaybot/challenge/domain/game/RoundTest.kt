package me.telegram.getplaybot.challenge.domain.game

import me.telegram.getplaybot.challenge.domain.MatchResult
import me.telegram.getplaybot.challenge.domain.predict
import me.telegram.getplaybot.challenge.domain.round
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RoundTest {
    @Test
    fun complete() {
        assertFalse(round(listOf(predict(result = null))).complete)
        assertFalse(round(listOf(predict(result = MatchResult(0, 0)), predict(result = null))).complete)
        assertTrue(round(listOf(predict(result = MatchResult(0, 0)), predict(result = MatchResult(0, 1)))).complete)
    }

    @Test
    fun computeLoose() {
        val predicts = listOf(predict(
            MatchResult(1, 0),
            MatchResult(3, 3),
            MatchResult(4, 6)
        ))

        val (home, away) = round(predicts).compute()

        assertEquals(0, home.first)
        assertEquals(3, away.first)
        assertTrue(home.second < away.second)
    }

    @Test
    fun computeTied() {
        val predicts = listOf(
            predict(MatchResult(1, 0), MatchResult(3, 0), MatchResult(2, 0)),
            predict(MatchResult(2, 2), MatchResult(4, 4), MatchResult(3, 3))
        )

        val (home, away) = round(predicts).compute()

        assertEquals(1, home.first)
        assertEquals(1, away.first)
        assertEquals(home.second, away.second)
    }

    @Test
    fun computeWin() {
        val predicts = listOf(
            predict(MatchResult(4, 0), MatchResult(6, 3), MatchResult(4, 3)),
            predict(MatchResult(1, 1), MatchResult(0, 0), MatchResult(2, 2))
        )

        val (home, away) = round(predicts).compute()

        assertEquals(3, home.first)
        assertEquals(0, away.first)
        assertTrue(home.second > away.second)
    }
}
