package me.telegram.getplaybot.challenge.models

data class League(val id: String, val teams: MutableList<Team> = mutableListOf())
