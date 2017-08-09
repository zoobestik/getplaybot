package me.telegram.getplaybot.lib

enum class IntWinner(val value: Int) {
    HOME(1), DRAW(0), AWAY(-1), ;

    companion object {
        fun valueOf(findValue: Int): IntWinner = values().first { it.value == findValue }
    }
}
