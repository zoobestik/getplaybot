package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.domain.game.User

suspend fun handleVote(user: User): String {
    return "vote" // @ToDo: отправить прогноз
}
