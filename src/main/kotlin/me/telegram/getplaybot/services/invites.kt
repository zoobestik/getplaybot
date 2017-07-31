package me.telegram.getplaybot.services.invites

import me.telegram.getplaybot.models.Invite
import me.telegram.getplaybot.models.User
import java.util.*
import java.util.UUID.randomUUID
import me.telegram.getplaybot.services.leagues.add as addToLeague

private val invites = mutableMapOf<String, Invite>()

class NoGoodCode : Exception()

suspend fun invite(user: User, leagueId: String): Invite {
    var uuid: String

    do uuid = randomUUID().toString()
    while (get(uuid) != null)

    val invite = Invite(user.id, uuid, leagueId)
    invites.put(invite.code, invite)

    return invite
}

suspend fun get(code: String): Invite? = invites[code]

suspend fun register(code: String, user: User): Invite {
    val invite = get(code)

    if (invite == null || !invite.isActive) throw NoGoodCode()

    addToLeague(invite.leagueId, user)

    invite.approveUserId = user.id
    invite.approveDate = Date()

    return invite
}