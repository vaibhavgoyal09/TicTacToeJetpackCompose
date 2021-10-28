package com.vaibhav.presentation.offline_mode.username

sealed class EnterPlayerNamesValidationEvent {

    data class Success(
        val player1Name: String,
        val player2Name: String
    ) : EnterPlayerNamesValidationEvent()
}
