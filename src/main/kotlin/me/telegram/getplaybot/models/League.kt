package me.telegram.getplaybot.models

data class League(val id: String, val teams: MutableList<Team> = mutableListOf())