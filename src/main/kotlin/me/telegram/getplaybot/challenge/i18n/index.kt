package me.telegram.getplaybot.challenge.i18n

fun welcome(name: String, body: String) =
    "Добро пожаловать, *$name*!\n\n" +
        "Ты можешь поуправлять мной отправляя следующие команды:\n\n" +
        body
