package me.telegram.getplaybot.challenge.models.game

enum class Permission {
    CORE, INVITE,
}

typealias Permissions = Map<Permission, Boolean>

val permissionsDefault = mapOf<Permission, Boolean>(
    Permission.CORE to true
)

val permissionsAdmin = permissionsDefault.plus(mapOf(
    Permission.INVITE to true
))
