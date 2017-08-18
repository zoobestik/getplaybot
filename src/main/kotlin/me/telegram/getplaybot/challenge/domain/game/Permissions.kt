package me.telegram.getplaybot.challenge.domain.game

enum class Permission {
    CORE, INVITE, LEAGUE,
}

typealias Permissions = Map<Permission, Boolean>

val permissionsDefault = mapOf(
    Permission.CORE to true
)

val permissionsAdmin = permissionsDefault + mapOf(
    Permission.INVITE to true,
    Permission.LEAGUE to true
)
