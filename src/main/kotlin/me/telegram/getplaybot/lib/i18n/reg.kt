package me.telegram.getplaybot.lib.i18n

val reg = mapOf(
        "no-code" to "Не нашлось кода активации. Попробуй:\n" +
                "`/reg [CODE]` — заменив *[CODE]* на что-то более приличное.",
        "no-invites" to "У вас нет инвайтов, поищите в другом месте :(",
        "invalid-code" to "Инвайт–ссылка сломалась или уже использована :("
)

fun reg_done(leagueId: String) = "Я добавил вас в турнир ($leagueId)!"
