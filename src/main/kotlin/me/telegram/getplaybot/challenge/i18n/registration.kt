package me.telegram.getplaybot.challenge.i18n.registration

import me.telegram.getplaybot.lib.i18n

private val list = mapOf(
    "invite-param-require" to "Не нашлось кода активации. Попробуй:\n" +
        "`/reg [CODE]` — заменив *[CODE]* на что-то более приличное.",
    "invite-code-invalid" to "*Инвайт–ссылка сломалась или уже использована :(*",
    "invite-codes-absent" to "*У вас нет инвайтов, поищите в другом месте :(*",
    "league-not-found" to "*Нельзя попасть туда, чего нет :(*"
)

fun i18n(key: String) = i18n(list, key)
fun done(name: String) = "Я добавил вас в турнир — $name!"
fun remains(count: Int) = "Инвайтов осталось – $count"
