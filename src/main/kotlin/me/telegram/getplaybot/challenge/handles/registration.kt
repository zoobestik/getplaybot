package me.telegram.getplaybot.challenge.handles.registration

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.registration.done
import me.telegram.getplaybot.challenge.labels.registration.label
import me.telegram.getplaybot.challenge.services.invites.*
import me.telegram.getplaybot.challenge.services.leagues.LeagueNotFound
import me.telegram.getplaybot.challenge.labels.registration.remains as remainsText

suspend fun handleRegisterInvite(user: User, botUsername: String, leagueId: String): List<String> {
    val messages = mutableListOf(registerInvite(user, botUsername, leagueId))
    val remains = countInvites(user)
    if (remains > 0) messages.add(remainsText(remains))
    return messages
}

suspend fun registerInvite(user: User, botUsername: String, leagueId: String): String {
    try {
        val code = invite(user, leagueId).code
        return "https://t.me/$botUsername?start=$code"
    } catch (e: InviteCodesAbsent) {
        return label("invite-codes-absent")
    }
}

suspend fun handleRegisterApprove(user: User, code: String): String {
    try {
        if (code.isEmpty()) return label("approve-code-require")
        val invite = register(code, user)
        return done(invite.leagueId)
    } catch (e: LeagueNotFound) {
        return label("approve-league-absent")
    } catch (e: InviteCodeInvalid) {
        return label("approve-code-invalid")
    }
}
