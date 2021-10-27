package com.vaibhav.presentation.online_mode

sealed class UserNameValidationEvent {

    data class Success(val userName: String): UserNameValidationEvent()
}
