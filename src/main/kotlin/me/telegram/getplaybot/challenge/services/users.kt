package me.telegram.getplaybot.challenge.services.users

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.domain.game.permissionsAdmin
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.api.objects.User as TelegramUser

private val log = LoggerFactory.getLogger("UsersServices")

val users = mutableMapOf<Int, User>(
    4809181 to User(4809181, null, permissions = permissionsAdmin)
)

suspend fun get(id: Int): User? = users[id]

suspend fun createOrUpdate(user: User): User {
    users.put(user.id, user)
    log.info("User updated {}", user)
    return user
}
