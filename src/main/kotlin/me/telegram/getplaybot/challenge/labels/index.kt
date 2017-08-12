package me.telegram.getplaybot.challenge.labels

import me.telegram.getplaybot.lib.label

private val general = mapOf(
    "unexpected-error" to "Что-то пошло не так! :("
)

fun label(key: String) = label(general, key)

fun welcome(name: String, body: String) =
    "Добро пожаловать, *$name*!\n\n" +
        "Ты можешь поуправлять отправляя следующие команды:\n" +
        body
