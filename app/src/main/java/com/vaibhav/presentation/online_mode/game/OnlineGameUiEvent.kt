package com.vaibhav.presentation.online_mode.game

sealed class OnlineGameUiEvent {

    data class ShowSnackBar(val message: String) : OnlineGameUiEvent()

    object ShowWinDialog: OnlineGameUiEvent()

    object ShowLostDialog: OnlineGameUiEvent()

    object ShowMatchDrawDialog: OnlineGameUiEvent()

    object NavigateUp: OnlineGameUiEvent()
}
