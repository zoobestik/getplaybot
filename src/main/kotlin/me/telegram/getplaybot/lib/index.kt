package me.telegram.getplaybot.lib

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <R : Any> R.logger(): Lazy<Logger> =
    lazy { LoggerFactory.getLogger(this::class.java.name) }

fun getEnv(name: String) = System.getenv(name) ?: ""

fun label(map: Map<String, String>, key: String) = map[key] ?: key

fun<T> buildArgsIterator(vararg list: T) = listOf(*list).iterator()

fun pairArgs(str: String): Pair<String, String> {
    val split = str.split(" ", limit = 2)
    return Pair(split.getOrElse(0, { "" }).trim(), split.getOrElse(1, { "" }).trim())
}
