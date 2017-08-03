package me.telegram.getplaybot.challenge.handles.registration

import me.telegram.getplaybot.challenge.i18n.registration.done
import me.telegram.getplaybot.challenge.i18n.registration.i18n
import me.telegram.getplaybot.challenge.models.User
import me.telegram.getplaybot.challenge.services.invites.InviteCodeInvalid
import me.telegram.getplaybot.challenge.services.invites.InviteCodesAbsent
import me.telegram.getplaybot.challenge.services.invites.invite
import me.telegram.getplaybot.challenge.services.invites.register
import me.telegram.getplaybot.challenge.services.leagues.LeagueNotFound
import me.telegram.getplaybot.challenge.services.leagues.TeamAlreadyIn
import me.telegram.getplaybot.lib.bindText

class InviteParamRequire : Exception()

val approveError = bindText { e: Exception ->
    i18n(
        when (e) {
            is InviteParamRequire -> "invite-param-require"
            is InviteCodesAbsent -> "invite-codes-absent"
            is InviteCodeInvalid -> "invite-code-invalid"
            is LeagueNotFound -> "league-not-found"
            is TeamAlreadyIn -> "team-already-in"
            else -> throw e
        }
    )
}

suspend fun handleRegisterInvite(user: User, botUsername: String): String {
    try {
        val code = invite(user, "champions-2017").code
        return "https://telegram.me/$botUsername?start=$code"
    } catch (e: Exception) {
        return approveError(e)
    }
}

suspend fun handleRegisterApprove(user: User, code: String): String {
    try {
        if (code.isEmpty()) throw InviteParamRequire()
        val invite = register(code, user)
        return done(invite.leagueId)
    } catch (e: Exception) {
        return approveError(e)
    }
}
