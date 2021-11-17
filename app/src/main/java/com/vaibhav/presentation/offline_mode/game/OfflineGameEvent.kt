package com.vaibhav.presentation.offline_mode.game

sealed class OfflineGameEvent {

    data class GameMove(val position: Int): OfflineGameEvent()
}
