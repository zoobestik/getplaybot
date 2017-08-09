package me.telegram.getplaybot.challenge.models

import me.telegram.getplaybot.lib.IntWinner
import me.telegram.getplaybot.lib.IntWinner.*
import org.junit.Assert.assertEquals
import org.junit.Test

class MatchResultTest {
    @Test
    fun scoresGraphs() {
        printGraph(MatchResult(0, 0))
        println()
        printGraph(MatchResult(1, 0))
        println()
        printGraph(MatchResult(2, 0))
        println()
        printGraph(MatchResult(3, 0))
        println()
        printGraph(MatchResult(3, 2))
        println()
        printGraph(MatchResult(3, 3))
        println()
        printGraph(MatchResult(6, 4))
    }

    @Test
    fun resultWinner() {
        assertEquals(HOME, IntWinner.valueOf(1))
        assertEquals(DRAW, IntWinner.valueOf(0))
        assertEquals(AWAY, IntWinner.valueOf(-1))
    }

    @Test
    fun winner() {
        assertEquals(HOME, MatchResult(2, 0).winner)
        assertEquals(DRAW, MatchResult(0, 0).winner)
        assertEquals(DRAW, MatchResult(1, 1).winner)
        assertEquals(AWAY, MatchResult(0, 3).winner)
    }

    @Test
    fun scores6vs4() {
        assertMatches(MatchResult(6, 4), listOf(
            /* 0-N */ -4, -3, -2, -1, 2, -1, -2,
            /* 1-N */ 9, -2, -1, 0, 3, 0, -1,
            /* 2-N */ 10, 11, 0, 1, 4, 1, 0,
            /* 3-N */ 11, 12, 13, 2, 5, 2, 1,
            /* 4-N */ 12, 13, 14, 15, 6, 3, 2,
            /* 5-N */ 13, 14, 15, 16, 19, 4, 3,
            /* 6-N */ 17, 18, 19, 20, 25, 20, 7
        ))
    }

    @Test
    fun scores2vs2() {
        assertMatches(MatchResult(2, 2), listOf(
            /* 0-N */ 14, 3, 7, 3, 2, 1, 0,
            /* 1-N */ 3, 16, 8, 4, 3, 2, 1,
            /* 2-N */ 7, 8, 25, 8, 7, 6, 5,
            /* 3-N */ 3, 4, 8, 16, 3, 2, 1,
            /* 4-N */ 2, 3, 7, 3, 14, 1, 0,
            /* 5-N */ 1, 2, 6, 2, 1, 12, -1,
            /* 6-N */ 0, 1, 5, 1, 0, -1, 10
        ))
    }

    @Test
    fun scores0vs0() {
        assertMatches(MatchResult(0, 0), listOf(
            /* 0-N */ 25, 8, 7, 6, 5, 4, 3,
            /* 1-N */ 8, 16, 3, 2, 1, 0, -1,
            /* 2-N */ 7, 3, 14, 1, 0, -1, -2,
            /* 3-N */ 6, 2, 1, 12, -1, -2, -3,
            /* 4-N */ 5, 1, 0, -1, 10, -3, -4,
            /* 5-N */ 4, 0, -1, -2, -3, 9, -5,
            /* 6-N */ 3, -1, -2, -3, -4, -5, 9
        ))
    }

    fun matches(block: (MatchResult) -> Unit) {
        for (i in 0..6)
            for (j in 0..6) {
                block(MatchResult(i, j))
            }
    }

    fun assertMatches(result: MatchResult, list: List<Int>) {
        var i = 0
        matches {
            assertEquals("For ${it.home}—${it.away}", list[i++], it.scores(result))
        }
    }

    fun printGraph(result: MatchResult) {
        matches {
            val scores = it.scores(result)
            println("${it.home} — ${it.away}" +
                (-25..25)
                    .map {
                        it in when {
                            scores > 0 -> 0..scores
                            scores < 0 -> scores..0
                            else -> 0..0
                        }
                    }
                    .map { if (it) '.' else ' ' }
                    .joinToString("")
            )
        }
    }
}
