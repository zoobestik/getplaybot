package me.telegram.getplaybot.challenge.handles

import me.telegram.getplaybot.challenge.i18n.reg
import me.telegram.getplaybot.challenge.i18n.reg_done
import me.telegram.getplaybot.challenge.models.Permission
import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.services.invites.NoGoodCode
import me.telegram.getplaybot.challenge.services.invites.invite
import me.telegram.getplaybot.challenge.services.invites.register

suspend fun handleRegisterInvite(user: User, botUsername: String): String {
    if (!user.check(Permission.INVITE))
        return reg.getOrDefault("no-invites", "no-invites")

    val code = invite(user, "champions-2017").code
    return "https://telegram.me/$botUsername?start=$code"
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
