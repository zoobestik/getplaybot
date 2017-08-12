package me.telegram.getplaybot.challenge.labels.registration

import me.telegram.getplaybot.lib.label

private val list = mapOf(
    "approve-code-require" to "Не нашлось кода активации. Попробуй:\n" +
        "`/reg CODE` — заменив *CODE* на что-то более приличное.",
    "approve-code-invalid" to "*Инвайт–ссылка сломалась или уже использована! :(*",
    "approve-league-absent" to "*Нельзя попасть туда, чего нет! :(*",
    "invite-codes-absent" to "*У вас нет инвайтов, поищите в другом месте! :(*"
)

fun label(key: String) = label(list, key)
fun done(name: String) = "Я добавил вас в турнир — $name!"
fun remains(count: Int) = "Инвайтов осталось – $count"