package me.telegram.getplaybot.handles

import me.telegram.getplaybot.lib.TelegramUser
import me.telegram.getplaybot.models.Permission
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.user.save

data class Feature(val name: String, val desc: String, val permission: Permission = Permission.CORE)

val features = listOf(
        Feature("scores", ""),
        Feature("invite", "", Permission.INVITE)
)

suspend fun handleWelcome(user: User, from: TelegramUser): String {
    user.username = from.userName
    user.firstName = from.firstName
    user.lastName = from.lastName

    save(user)

    return "Welcome to “Get To Play Challenge”, ${user.name}!\n\n" +
            "Available commands list:\n" + body(user) + "\n" +
            "  /..."
}

private fun body(user: User) = features
        .filter { user.check(it.permission) }
        .map { "  /${it.name} — ${it.desc}" }
        .joinToString("\n")
