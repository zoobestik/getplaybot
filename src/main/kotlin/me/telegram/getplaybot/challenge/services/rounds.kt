package me.telegram.getplaybot.challenge.services.rounds

import me.telegram.getplaybot.challenge.domain.game.Round
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("RoundsServices")

private val matches = mutableMapOf<String, Round>()

suspend fun last(id: String): Round? = matches[id]

suspend fun save(round: Round): Round {
    matches.put(round.id, round)
    log.info("Round saved {}", round)
    return round
}
