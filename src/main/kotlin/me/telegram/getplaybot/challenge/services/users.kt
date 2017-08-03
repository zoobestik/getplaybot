package me.telegram.getplaybot.challenge.services.users

import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.models.permissionsAdmin
import org.telegram.telegrambots.api.objects.User as TelegramUser

private val users = mutableMapOf<Int, User>(
    4809181 to User(4809181, null, permissions = permissionsAdmin)
)

suspend fun get(id: Int): User? = users[id]

suspend fun save(user: User): User {
    users.put(user.id, user)
    return user
}
