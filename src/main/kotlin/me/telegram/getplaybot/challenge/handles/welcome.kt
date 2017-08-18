package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.bot.BotHandle
import me.telegram.getplaybot.challenge.bot.BotHandlers
import me.telegram.getplaybot.challenge.domain.game.Permission
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.welcome
import me.telegram.getplaybot.challenge.services.users.createOrUpdate
import me.telegram.getplaybot.lib.TelegramUser

abstract class FeatureGroup(val permission: Permission = Permission.CORE) {
    abstract protected fun filter(bot: BotHandle): Boolean
    fun list(handlers: BotHandlers): List<BotHandle> = handlers.handlers.filter(this::filter)
}

private fun featuresShort(bot: BotHandle) = listOf("league", "day", "vote", "help").contains(bot.name)
private fun featuresHide(bot: BotHandle) = bot.name.startsWith("--")

private val shortGroups = listOf(object : FeatureGroup() {
    override fun filter(bot: BotHandle): Boolean = featuresShort(bot)
})

private val longGroups = listOf(
    object : FeatureGroup() {
        override fun filter(bot: BotHandle): Boolean = !featuresHide(bot)
    },
    object : FeatureGroup(Permission.INVITE) {
        override fun filter(bot: BotHandle): Boolean = featuresHide(bot) && bot.name.contains("invite")
    },
    object : FeatureGroup(Permission.LEAGUE) {
        override fun filter(bot: BotHandle): Boolean = featuresHide(bot) && bot.name.contains("league")
    }
)

private fun helpCommands(user: User, handlers: BotHandlers, groups: List<FeatureGroup>) = groups
    .filter { user.check(it.permission) }
    .fold(listOf<BotHandle>()) { acc, group -> acc + group.list(handlers) }
    .filter { it.name != "start" }
    .map { it.description }
    .joinToString("\n")

suspend fun handleWelcome(handlers: BotHandlers, chatUser: User, from: TelegramUser): String {
    val user = createOrUpdate(chatUser.copy(
        username = from.userName,
        firstName = from.firstName,
        lastName = from.lastName
    ))

    return welcome(user.name, helpCommands(user, handlers, shortGroups))
}

suspend fun handleHelp(user: User, handlers: BotHandlers): String = helpCommands(user, handlers, longGroups)
