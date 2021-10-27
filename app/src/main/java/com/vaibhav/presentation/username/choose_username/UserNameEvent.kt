package com.vaibhav.presentation.username.choose_username

sealed class UserNameEvent {

    data class EnteredUserName(val userName: String): UserNameEvent()

    object ValidateUserName: UserNameEvent()
}