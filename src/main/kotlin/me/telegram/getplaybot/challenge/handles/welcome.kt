package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.domain.game.Permission
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.handles.label
import me.telegram.getplaybot.challenge.labels.welcome
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
    Feature("league"),
    Feature("day"),
    Feature("vote"),
    Feature("help")
)

val longHelp = shortHelp + listOf(
    Feature("reg"),
    Feature("invite", Permission.INVITE),
    Feature("leagues", Permission.LEAGUE, listOf(
        "leagues",
        "addleague"
    ))
)

private fun helpCommands(user: User, features: List<Feature>) = features
    .filter { user.check(it.permission) }
    .fold(listOf<ChallengeCommand>()) { list, feature -> list + feature.commands }
    .joinToString("\n") { id ->
        label(id).let { desc ->
            "/$id" + if (id != desc) " — $desc" else ""
        }
    }

suspend fun handleWelcome(chatUser: User, from: TelegramUser): String {
    val user = createOrUpdate(chatUser.copy(
        username = from.userName,
        firstName = from.firstName,
        lastName = from.lastName
    ))

    return welcome(user.name, helpCommands(user, shortHelp))
}

suspend fun handleHelp(user: User): String = helpCommands(user, longHelp)
