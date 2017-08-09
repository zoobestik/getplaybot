package me.telegram.getplaybot.challenge.models

import org.junit.Test

internal class PredictTest {
    @Test
    fun getComputedHome() {
    }

    @Test
    fun getComputedAway() {
    }

    @Test
    fun calculateWithNotComplete() {
        Predict(MatchResult(4, 0), MatchResult(6, 3), null).compute()
    }

}
