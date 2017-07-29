package me.telegram.getplaybot.models

import java.util.*

data class Invite(
        val senderId: Int,
        val league: String,
        val code: String,
        var approveUserId: Int? = null,
        var approveDate: Date? = null
) {
    val isActive: Boolean
        get() = approveDate == null
}
