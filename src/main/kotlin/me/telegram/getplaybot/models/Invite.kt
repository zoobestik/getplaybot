package me.telegram.getplaybot.models

import java.util.*

data class Invite(
        val senderId: Int,
        val code: String,
        val leagueId: String,
        var approveUserId: Int? = null,
        var approveDate: Date? = null
) {
    val isActive: Boolean
        get() = approveDate == null
}
