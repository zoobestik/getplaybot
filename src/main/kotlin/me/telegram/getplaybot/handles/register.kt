package me.telegram.getplaybot.handles

import me.telegram.getplaybot.lib.i18n.reg
import me.telegram.getplaybot.lib.i18n.reg_done
import me.telegram.getplaybot.models.Permission
import me.telegram.getplaybot.models.User
import me.telegram.getplaybot.services.invites.NoGoodCode
import me.telegram.getplaybot.services.invites.invite
import me.telegram.getplaybot.services.invites.register

suspend fun handleRegisterInvite(user: User): String {
    if (!user.check(Permission.INVITE))
        return reg.getOrDefault("no-invites", "no-invites")

    return invite(user, "champions-2017").code
}

suspend fun handleRegisterApprove(user: User, code: String): String {
    if (code.isEmpty())
        return reg.getOrDefault("no-code", "no-code")

    try {
        val invite = register(code, user)
        return reg_done(invite.leagueId)
    } catch (e: NoGoodCode) {
        return reg.getOrDefault("invalid-code", "invalid-code")
    }
}
