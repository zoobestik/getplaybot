package me.telegram.getplaybot.challenge.models.game

import java.util.*

data class Invite(
    val senderId: Int,
    val code: String,
    val leagueId: String,
    var approveUserId: Int? = null,
    var approveDate: Date? = null
) {
    val isActive
        get() = approveDate == null
}
