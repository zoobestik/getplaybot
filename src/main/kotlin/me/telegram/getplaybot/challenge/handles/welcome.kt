package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.domain.game.Permission
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.i18n.commands.i18n
import me.telegram.getplaybot.challenge.i18n.welcome
import me.telegram.getplaybot.challenge.services.users.createOrUpdate
import me.telegram.getplaybot.lib.TelegramUser

typealias ChallengeCommand = String

data class Feature(
    val name: String,
    val permission: Permission = Permission.CORE,
    val _commands: List<ChallengeCommand>? = null
) {
    val commands: List<ChallengeCommand>
        get() = _commands ?: listOf(name)
}

val shortHelp = listOf(
    Feature("scores"),
    Feature("day"),
    Feature("vote"),
    Feature("help")
)

val longHelp = shortHelp + listOf(
    Feature("reg"),
    Feature("invite", Permission.INVITE),
    Feature("leagues", Permission.LEAGUE, listOf(
        "leagues",
        "leagueadd"
    ))
)

private fun helpCommands(user: User, features: List<Feature>) = features
    .filter { user.check(it.permission) }
    .fold(listOf<ChallengeCommand>()) { list, feature -> list + feature.commands }
    .map {
        val desc = i18n(it)
        "/$it" + if (desc != it) " — $desc" else ""
    }
    .joinToString("\n")

suspend fun handleWelcome(chatUser: User, from: TelegramUser): String {
    val user = createOrUpdate(chatUser.copy(
        username = from.userName,
        firstName = from.firstName,
        lastName = from.lastName
    ))

    return welcome(user.name, helpCommands(user, shortHelp))
}

suspend fun handleHelp(user: User): String {
    return helpCommands(user, longHelp)
}
