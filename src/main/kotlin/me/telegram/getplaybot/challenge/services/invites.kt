package me.telegram.getplaybot.challenge.services.invites

import me.telegram.getplaybot.challenge.models.Invite
import me.telegram.getplaybot.challenge.models.Permission
import me.telegram.getplaybot.challenge.models.User
import java.util.*
import java.util.UUID.randomUUID
import me.telegram.getplaybot.challenge.services.leagues.add as addToLeague

private val invites = mutableMapOf<String, Invite>()

class InviteCodeInvalid : Exception()
class InviteCodesAbsent : Exception()

suspend fun invite(user: User, leagueId: String): Invite {
    if (!user.check(Permission.INVITE))
        throw InviteCodesAbsent()

    var uuid: String

    do uuid = randomUUID().toString()
    while (get(uuid) != null)

    val invite = Invite(user.id, uuid, leagueId)
    invites.put(invite.code, invite)

    return invite
}

suspend fun get(code: String): Invite? = invites[code]

suspend fun countInvites(user: User) = 0

suspend fun register(code: String, user: User): Invite {
    val invite = get(code)
    if (invite == null || !invite.isActive) throw InviteCodeInvalid()

    addToLeague(invite.leagueId, user)

    invite.approveUserId = user.id
    invite.approveDate = Date()

    return invite
}
