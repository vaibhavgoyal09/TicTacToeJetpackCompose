package com.vaibhav.presentation.online_mode

sealed class UserNameEvent {

    data class EnteredUserName(val userName: String): UserNameEvent()

    object ValidateUserName: UserNameEvent()
}