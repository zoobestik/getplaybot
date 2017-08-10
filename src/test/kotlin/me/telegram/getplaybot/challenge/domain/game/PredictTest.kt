package me.telegram.getplaybot.challenge.domain.game

import me.telegram.getplaybot.challenge.domain.MatchResult
import me.telegram.getplaybot.challenge.domain.match
import org.junit.Test
import kotlin.test.*

internal class PredictTest {
    @Test
    fun defaultPredictTest() {
        assertEquals(MatchResult(0, 0), defaultPredict())
    }

    @Test
    fun completeTest() {
        assertFalse(Predict(match(null), MatchResult(0, 1), MatchResult(1, 0)).complete)
        assertTrue(Predict(match(MatchResult(1, 0)), MatchResult(1, 1), MatchResult(0, 1)).complete)
    }

    @Test
    fun computedWithValue() {
        val home = MatchResult(0, 1)
        val away = MatchResult(1, 0)
        val predict = Predict(match(null), home, away)

        assertTrue(predict.home === home)
        assertTrue(predict.computedHome === home)

        assertTrue(predict.away === away)
        assertTrue(predict.computedAway === away)
    }

    @Test
    fun computedWithDefault() {
        val predict = Predict(match(null), null, null)

        assertNull(predict.home)
        assertEquals(predict.computedHome, defaultPredict())

        assertNull(predict.away)
        assertEquals(predict.computedAway, defaultPredict())
    }

    @Test
    fun calculateWithNotComplete() {
        assertFailsWith<PredictNotComplete> {
            Predict(match(null), MatchResult(4, 0), MatchResult(6, 3)).compute()
        }
    }
}
