package com.vaibhav.presentation.online_mode.username

sealed class UserNameValidationEvent {

    data class Success(val userName: String): UserNameValidationEvent()
}
