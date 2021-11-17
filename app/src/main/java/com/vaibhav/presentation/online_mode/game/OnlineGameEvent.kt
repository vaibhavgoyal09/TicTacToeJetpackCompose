package com.vaibhav.presentation.online_mode.game

sealed class OnlineGameEvent {

    data class GameMove(val position: Int): OnlineGameEvent()
}
