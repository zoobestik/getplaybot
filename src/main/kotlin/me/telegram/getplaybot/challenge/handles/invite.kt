package me.telegram.getplaybot.challenge.handles.invite

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.labels.invite.done
import me.telegram.getplaybot.challenge.labels.invite.inviteNoLeague
import me.telegram.getplaybot.challenge.labels.invite.label
import me.telegram.getplaybot.challenge.services.invites.*
import me.telegram.getplaybot.challenge.services.leagues.LeagueNotFound
import me.telegram.getplaybot.challenge.services.leagues.list
import me.telegram.getplaybot.lib.BindReplyWithCallbacks
import me.telegram.getplaybot.lib.ReplyCallbackButton
import me.telegram.getplaybot.lib.telegramTwoColumns
import me.telegram.getplaybot.challenge.labels.invite.remains as remainsText
import me.telegram.getplaybot.challenge.services.leagues.get as getLeague
import me.telegram.getplaybot.lib.buildArgsIterator as answers

private typealias Answer = suspend (BindReplyWithCallbacks) -> String

suspend fun handleInvite(user: User, bot: String, id: String): Iterator<Answer> {
    try {
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

        val league = getLeague(leagueId) ?: return answers<Answer>({
            inviteNoLeague(leagueId)
        })

        val code = invite(user, league).code
        val message = "$bot?start=$code"
        val remains = countInvites(user)

        var messages = arrayOf<Answer>({ message })
        if (remains > 0)
            messages += { remainsText(remains) }

        return answers(*messages)
    } catch (e: InviteCodesAbsent) {
        return answers<Answer>({ label("invite-codes-absent") })
    }
}

suspend fun handleApproveInvite(user: User, code: String): String {
    try {
        if (code.isEmpty()) return label("approve-code-require")
        val invite = register(code, user)
        return done(invite.league.id)
    } catch (e: LeagueNotFound) {
        return label("approve-league-absent")
    } catch (e: InviteCodeInvalid) {
        return label("approve-code-invalid")
    }
}
