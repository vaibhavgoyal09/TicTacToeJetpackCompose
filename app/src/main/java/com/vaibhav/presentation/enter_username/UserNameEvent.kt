package com.vaibhav.presentation.enter_username

sealed class UserNameEvent {

    data class EnteredUserName(val userName: String): UserNameEvent()

    object ValidateUserName: UserNameEvent()
}