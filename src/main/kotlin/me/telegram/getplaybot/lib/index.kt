package me.telegram.getplaybot.lib

fun getEnv(name: String) = System.getenv(name) ?: ""

fun pairArgs(str: String): Pair<String, String> {
    val split = str.split(" ", limit = 2)
    return Pair(split.getOrElse(0, { "" }).trim(), split.getOrElse(1, { "" }).trim())
}
