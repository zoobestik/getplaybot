package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.i18n.commands.i18n
import me.telegram.getplaybot.challenge.i18n.welcome
import me.telegram.getplaybot.challenge.models.Permission
import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.services.users.save
import me.telegram.getplaybot.lib.TelegramUser

data class Feature(val name: String, val permission: Permission = Permission.CORE)

val features = listOf(
    Feature("scores"),
    Feature("reg"),
    Feature("invite", Permission.INVITE)
)

suspend fun handleWelcome(user: User, from: TelegramUser): String {
    user.username = from.userName
    user.firstName = from.firstName
    user.lastName = from.lastName

    save(user)

    return welcome(user.name, list(user))
}

private fun list(user: User) = features
    .filter { user.check(it.permission) }
    .map { "/${it.name} — ${i18n(it.name)}" }
    .joinToString("\n")
