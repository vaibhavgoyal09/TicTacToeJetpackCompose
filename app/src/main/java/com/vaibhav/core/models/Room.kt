package com.vaibhav.core.models

data class Room(
    val name: String,
    val playersCount: Int = 1
) {

    enum class GamePhase {
        WAITING_FOR_PLAYERS,
        NEW_ROUND,
        WAITING_FOR_START,
        GAME_RUNNING
    }
}
