package me.telegram.getplaybot.lib

inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? = input?.let(callback)

fun getEnv(name: String) = System.getenv(name) ?: ""