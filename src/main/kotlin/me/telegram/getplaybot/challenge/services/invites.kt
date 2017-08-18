package me.telegram.getplaybot.challenge.services.invites

import me.telegram.getplaybot.challenge.domain.game.Invite
import me.telegram.getplaybot.challenge.domain.game.Permission
import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.domain.game.leagues.League
import me.telegram.getplaybot.challenge.services.leagues.addTeam
import org.slf4j.LoggerFactory
import java.util.*
import java.util.UUID.randomUUID
import me.telegram.getplaybot.challenge.services.team.new as createTeam

private val log = LoggerFactory.getLogger("InvitesServices")

private val invites = mutableMapOf<String, Invite>()

class InviteCodeInvalid : Exception()
class InviteCodesAbsent : Exception()

suspend fun invite(user: User, league: League): Invite {
    if (!user.check(Permission.INVITE))
        throw InviteCodesAbsent()

    var uuid: String

    do uuid = randomUUID().toString()
    while (get(uuid) != null)

    val invite = Invite(uuid, league, user)
    invites.put(invite.code, invite)

    log.info("Invite generated {}", invite)

    return invite
}

suspend fun get(code: String): Invite? = invites[code]

suspend fun countInvites(user: User) = 0

suspend fun register(code: String, user: User): Invite {
    val invite = get(code)
    if (invite == null || !invite.isActive) throw InviteCodeInvalid()

    addTeam(invite.league.id, createTeam(user))

    invite.approveDate = Date()
    invite.approveUser = user

    log.info("Invite approved {}", invite)

    return invite
}
