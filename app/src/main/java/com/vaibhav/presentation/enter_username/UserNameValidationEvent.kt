package com.vaibhav.presentation.enter_username

sealed class UserNameValidationEvent {

    data class Success(val userName: String): UserNameValidationEvent()
}
