package me.telegram.getplaybot.handles

import me.telegram.getplaybot.models.Permission
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.invites.invite
import me.telegram.getplaybot.services.invites.register

suspend fun handleRegisterInvite(user: User): String {
    if (user.check(Permission.INVITE)) {
        return invite(user).code
    }
    throw Exception("Not permission")
}

suspend fun handleRegisterApprove(code: String, user: User): String {
    register(code, user)
    return "registred"
}
