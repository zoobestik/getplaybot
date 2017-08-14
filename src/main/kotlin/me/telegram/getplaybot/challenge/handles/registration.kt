package me.telegram.getplaybot.challenge.handles.registration

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.registration.done
import me.telegram.getplaybot.challenge.labels.registration.label
import me.telegram.getplaybot.challenge.services.invites.*
import me.telegram.getplaybot.challenge.services.leagues.LeagueNotFound
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.lib.BindReplyWithCallbacks
import me.telegram.getplaybot.lib.ReplyCallbackButton
import me.telegram.getplaybot.lib.buildArgsIterator as answers
import me.telegram.getplaybot.lib.telegramTwoColumns
import me.telegram.getplaybot.challenge.labels.registration.remains as remainsText

private typealias Answer = (BindReplyWithCallbacks) -> String

suspend fun handleRegisterInvite(user: User, bot: String, id: String): Iterator<Answer> {
    var leagueId = id
    if (leagueId.isEmpty()) {
        val list = list()
        when (list.size) {
            1 -> leagueId = list[0].id
            0 -> return answers<Answer>({ label("invite-no-leagues") })
            else -> return answers({ reply ->
                reply({
                    telegramTwoColumns(list) { (id, name) ->
                        ReplyCallbackButton(name, id)
                    }
                })
                label("invite-choose-league")
            })
        }
    }

    val message = registerInvite(user, bot, leagueId)
    val remains = countInvites(user)

    var messages = arrayOf<Answer>({ message })
    if (remains > 0)
        messages += { remainsText(remains) }

    return answers(*messages)
}

suspend fun registerInvite(user: User, bot: String, leagueId: String): String {
    try {
        val code = invite(user, leagueId).code
        return "$bot?start=$code"
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
