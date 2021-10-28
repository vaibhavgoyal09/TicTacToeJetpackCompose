package com.vaibhav.presentation.offline_mode.username

sealed class EnterPlayerNamesEvent {

    data class EnteredPlayer1Name(val name: String): EnterPlayerNamesEvent()

    data class EnteredPlayer2Name(val name: String): EnterPlayerNamesEvent()

    object ValidateUserNames: EnterPlayerNamesEvent()
}
