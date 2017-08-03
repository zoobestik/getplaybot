package me.telegram.getplaybot.challenge.i18n.registration

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "invite-param-require" to "Не нашлось кода активации. Попробуй:\n" +
        "`/reg [CODE]` — заменив *[CODE]* на что-то более приличное.",
    "invite-codes-absent" to "У вас нет инвайтов, поищите в другом месте :(",
    "invite-code-invalid" to "Инвайт–ссылка сломалась или уже использована :(",
    "league-not-found" to "Нельзя попасть туда, чего нет :(",
    "team-already-in" to "Вы уже там, поделитесь инвайтом с другом :)"
)

fun i18n(key: String) = i18n(list, key)
fun done(name: String) = "Я добавил вас в турнир — $name!"
