package me.telegram.getplaybot.challenge.bot

import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto

typealias MessageBody = suspend (SendMessage) -> Unit
typealias MessageTextBody = suspend (SendMessage) -> String
typealias MessagePhotoBody = suspend (SendPhoto) -> Unit
