package me.telegram.getplaybot.challenge.bot

import me.telegram.getplaybot.challenge.domain.game.User
import me.telegram.getplaybot.challenge.handles.*
import me.telegram.getplaybot.challenge.handles.invite.handleApproveInvite
import me.telegram.getplaybot.challenge.handles.invite.handleInvite
import org.telegram.telegrambots.api.objects.Message

fun handlers(bot: ChallengeHandlers) = BotHandlers(bot, listOf(
    object : BotHandle("start") {
        suspend override fun execute(message: Message, user: User, payload: String) {
            var postText: String? = null
            if (payload.isNotEmpty()) postText = handleApproveInvite(user, payload)
            bot.markdown(message) { handleWelcome(user, message.from) }
            if (postText != null) bot.markdown(message) { postText as String }
        }
    },
    object : BotHandle("help") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleHelp(user)
        }
    },

    object : BotHandle("league") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleLeague(user, payload, this.replyCallbacks(it))
        }
    },
    object : BotHandle("day") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleDay(user, payload)
        }
    },
    object : BotHandle("vote") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleVote(user)
        }
    },
    object : BotHandle("invite") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleApproveInvite(user, payload)
        }
    },

    object : BotHandle("--list-leagues") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleLeagues()
        }
    },
    object : BotHandle("--get-invite") {
        suspend override fun execute(message: Message, user: User, payload: String) {
            bot.markdown(message, handleInvite(user, bot.link, payload)) { next, outputMessage ->
                next(this.replyCallbacks(outputMessage))
            }
        }
    },
    object : BotHandle("--create-league") {
        suspend override fun execute(message: Message, user: User, payload: String) = bot.markdown(message) {
            handleCreateLeague(payload)
        }
    }
))
