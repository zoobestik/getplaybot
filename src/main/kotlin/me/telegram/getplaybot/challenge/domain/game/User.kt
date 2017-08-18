package me.telegram.getplaybot.challenge.domain.game

typealias ChatStateMachine = Pair<String, Any>

data class User(
    val id: Int,
    val chatId: Long? = null,
    val username: String? = null,
    val firstName: String = "Alan",
    val lastName: String = "Smithee",
    val defaultLeagueId: String? = null,
    val wizard: ChatStateMachine? = null,
    val permissions: Permissions = permissionsDefault
) {
    val name: String
        get() = if (username == null || username.isEmpty()) lastName + firstName else username

    fun check(name: Permission) = permissions[name] ?: false
}
