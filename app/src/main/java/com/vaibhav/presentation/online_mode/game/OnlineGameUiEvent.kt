package com.vaibhav.presentation.online_mode.game

sealed class OnlineGameUiEvent {

    data class ShowSnackBar(val message: String) : OnlineGameUiEvent()

    data class Navigate(val route: String) : OnlineGameUiEvent()

    object ShowWinDialog: OnlineGameUiEvent()

    object ShowLostDialog: OnlineGameUiEvent()

    object ShowMatchDrawDialog: OnlineGameUiEvent()

    object NavigateUp: OnlineGameUiEvent()
}
