package com.vaibhav.core.models

data class Room(
    val name: String,
    val playersCount: Int = 1
) {

    enum class Phase {
        WAITING_FOR_PLAYER,
    }
}
