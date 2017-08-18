package me.telegram.getplaybot.challenge.domain.game

import me.telegram.getplaybot.challenge.domain.game.leagues.League
import java.util.*

class Invite(
    val code: String,
    val league: League,
    val sender: User,
    var approveUser: User? = null,
    var approveDate: Date? = null
) {
    val isActive
        get() = approveDate == null
}
