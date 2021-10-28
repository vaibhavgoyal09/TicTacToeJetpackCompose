package com.vaibhav.presentation.online_mode.username

sealed class ChooseUserNameEvent {

    data class EnteredUserName(val userName: String): ChooseUserNameEvent()

    object ValidateUserName: ChooseUserNameEvent()
}