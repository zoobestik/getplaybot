package me.telegram.getplaybot.services.invites

import me.telegram.getplaybot.models.Invite
import me.telegram.getplaybot.models.User
import java.util.*
import java.util.UUID.randomUUID

private val invites = mutableMapOf<String, Invite>()

suspend fun invite(user: User): Invite {
    var uuid: String
    do uuid = randomUUID().toString()
    while (get(uuid) != null)

    val invite = Invite(user.id, "", uuid)
    invites.put(invite.code, invite)
    return invite
}

suspend fun get(code: String): Invite? = invites[code]

suspend fun register(code: String, user: User) {
    val invite = get(code)

    if (invite != null && invite.isActive) {
        invite.approveUserId = user.id
        invite.approveDate = Date()
        return
    }

    throw Throwable("Not a code")
}
