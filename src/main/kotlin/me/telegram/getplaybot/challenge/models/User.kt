package me.telegram.getplaybot.challenge.models

data class User(
    val id: Int,
    val chatId: Long?,
    var username: String = "",
    var firstName: String = "",
    var lastName: String = "",
    val permissions: Permissions = permissionsDefault
) {
    val name = if (username.isEmpty()) lastName + firstName else username
    fun check(name: Permission) = permissions.getOrDefault(name, false)
}
