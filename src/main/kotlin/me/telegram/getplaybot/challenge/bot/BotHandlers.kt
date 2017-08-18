package me.telegram.getplaybot.challenge.bot

data class BotHandlers(val bot: ChallengeHandlers, val handlers: List<BotHandle>) {
    fun get(text: String) = handlers.find { it.isDo(text) }
}
