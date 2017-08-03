package me.telegram.getplaybot.lib

fun i18n(map: Map<String, String>, key: String) = map[key] ?: key
fun <T> bindText(block: (T) -> String) = fun(arg: T): String = block(arg)
