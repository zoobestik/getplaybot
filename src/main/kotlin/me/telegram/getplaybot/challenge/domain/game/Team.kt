package me.telegram.getplaybot.challenge.domain.game

data class Team(
    val id: String,
    val user: User?
) {
    val name
        get() = user?.name ?: "Team $id"
}
