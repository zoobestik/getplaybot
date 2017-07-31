package me.telegram.getplaybot.handles

import me.telegram.getplaybot.lib.TelegramUser
import me.telegram.getplaybot.lib.i18n.commands
import me.telegram.getplaybot.lib.i18n.welcome_prefix
import me.telegram.getplaybot.models.Permission
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.users.save

data class Feature(val name: String, val permission: Permission = Permission.CORE)

val features = listOf(
        Feature("scores"),
        Feature("reg"),
        Feature("invite", Permission.INVITE)
)

suspend fun handleWelcome(user: User, from: TelegramUser, payload: String): String {
    user.username = from.userName
    user.firstName = from.firstName
    user.lastName = from.lastName

    save(user)

    if (payload.isNotEmpty()) {
        //...
    }

    return welcome_prefix(user.name) + "\n\n" + list(user)
}

private fun list(user: User) = features
        .filter { user.check(it.permission) }
        .map { "/${it.name} — ${commands.getOrDefault(it.name, it.name)}" }
        .joinToString("\n")
