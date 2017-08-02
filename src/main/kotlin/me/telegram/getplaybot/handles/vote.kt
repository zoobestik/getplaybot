package me.telegram.getplaybot.handles

import me.telegram.getplaybot.models.User


suspend fun handleVote(user: User): String {
    return "vote" // @ToDo: отправить прогноз
}
