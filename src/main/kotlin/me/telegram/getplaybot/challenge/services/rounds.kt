package me.telegram.getplaybot.challenge.services.rounds

import me.telegram.getplaybot.challenge.models.game.Round

private val matches = mutableMapOf<String, Round>()

suspend fun last(id: String): Round? = matches[id]

suspend fun save(round: Round): Round {
    matches.put(round.id, round)
    return round
}
