package me.telegram.getplaybot.challenge.models

import org.junit.Test
import org.junit.Assert.*

class MatchResultTest {
    @Test
    fun getWinner() {
    }

    @Test
    fun scoresWithGoals() {
        val result = MatchResult(6, 4)

        assertEquals(14, MatchResult(6, 6).scores(result))
        assertEquals(34, MatchResult(6, 5).scores(result))
        assertEquals(40, MatchResult(6, 4).scores(result))
        assertEquals(33, MatchResult(6, 3).scores(result))
        assertEquals(32, MatchResult(6, 2).scores(result))
        assertEquals(31, MatchResult(6, 1).scores(result))
        assertEquals(30, MatchResult(6, 0).scores(result))

        assertEquals(5, MatchResult(5, 6).scores(result))
        assertEquals(5, MatchResult(5, 5).scores(result))
        assertEquals(32, MatchResult(5, 4).scores(result))
        assertEquals(25, MatchResult(5, 3).scores(result))
        assertEquals(25, MatchResult(5, 2).scores(result))
        assertEquals(25, MatchResult(5, 1).scores(result))
        assertEquals(25, MatchResult(5, 0).scores(result))

        assertEquals(5, MatchResult(4, 6).scores(result))
        assertEquals(5, MatchResult(4, 5).scores(result))
        assertEquals(11, MatchResult(4, 4).scores(result))
        assertEquals(25, MatchResult(4, 3).scores(result))
        assertEquals(25, MatchResult(4, 2).scores(result))
        assertEquals(25, MatchResult(4, 1).scores(result))
        assertEquals(24, MatchResult(4, 0).scores(result))

        assertEquals(5, MatchResult(3, 6).scores(result))
        assertEquals(5, MatchResult(3, 5).scores(result))
        assertEquals(10, MatchResult(3, 4).scores(result))
        assertEquals(5, MatchResult(3, 3).scores(result))
        assertEquals(25, MatchResult(3, 2).scores(result))
        assertEquals(24, MatchResult(3, 1).scores(result))
        assertEquals(23, MatchResult(3, 0).scores(result))

        assertEquals(5, MatchResult(2, 6).scores(result))
        assertEquals(5, MatchResult(2, 5).scores(result))
        assertEquals(9, MatchResult(2, 4).scores(result))
        assertEquals(5, MatchResult(2, 3).scores(result))
        assertEquals(4, MatchResult(2, 2).scores(result))
        assertEquals(23, MatchResult(2, 1).scores(result))
        assertEquals(22, MatchResult(2, 0).scores(result))

        assertEquals(5, MatchResult(1, 6).scores(result))
        assertEquals(5, MatchResult(1, 5).scores(result))
        assertEquals(8, MatchResult(1, 4).scores(result))
        assertEquals(4, MatchResult(1, 3).scores(result))
        assertEquals(3, MatchResult(1, 2).scores(result))
        assertEquals(2, MatchResult(1, 1).scores(result))
        assertEquals(21, MatchResult(1, 0).scores(result))

        assertEquals(4, MatchResult(0, 6).scores(result))
        assertEquals(4, MatchResult(0, 5).scores(result))
        assertEquals(7, MatchResult(0, 4).scores(result))
        assertEquals(3, MatchResult(0, 3).scores(result))
        assertEquals(2, MatchResult(0, 2).scores(result))
        assertEquals(1, MatchResult(0, 1).scores(result))
        assertEquals(0, MatchResult(0, 0).scores(result))
    }

    @Test
    fun scoresWithWhitewash() {
        val result = MatchResult(5, 0)

        assertEquals(MatchResult(5, 0).scores(result), 25)
        assertEquals(MatchResult(5, 4).scores(result), 18)
        assertEquals(MatchResult(5, 0).scores(result), 15)
        assertEquals(MatchResult(5, 5).scores(result), 5)
        assertEquals(MatchResult(3, 5).scores(result), 20)
        assertEquals(MatchResult(0, 5).scores(result), 3)
        assertEquals(MatchResult(0, 0).scores(result), 0)
    }

    @Test
    fun scoresDrawWithGoals() {
        val result = MatchResult(2, 2)

        assertEquals(MatchResult(2, 2).scores(result), 25)
        assertEquals(MatchResult(5, 4).scores(result), 18)
        assertEquals(MatchResult(5, 0).scores(result), 15)
        assertEquals(MatchResult(5, 5).scores(result), 5)
        assertEquals(MatchResult(3, 5).scores(result), 20)
        assertEquals(MatchResult(0, 5).scores(result), 3)
        assertEquals(MatchResult(0, 0).scores(result), 0)
    }

    @Test
    fun scoresWithZeros() {
        val result = MatchResult(0, 0)

        assertEquals(MatchResult(0, 0).scores(result), 25)
        assertEquals(MatchResult(5, 4).scores(result), 18)
        assertEquals(MatchResult(5, 0).scores(result), 15)
        assertEquals(MatchResult(5, 5).scores(result), 5)
        assertEquals(MatchResult(3, 5).scores(result), 20)
        assertEquals(MatchResult(0, 5).scores(result), 3)
        assertEquals(MatchResult(0, 0).scores(result), 0)
    }
}
