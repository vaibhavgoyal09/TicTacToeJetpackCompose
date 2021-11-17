package com.vaibhav.presentation.offline_mode.game

sealed class OfflineScreenUiEvent {

    data class ShowWinDialog(val winnerUserName: String) : OfflineScreenUiEvent()

    object ShowMatchDrawDialog: OfflineScreenUiEvent()
}
