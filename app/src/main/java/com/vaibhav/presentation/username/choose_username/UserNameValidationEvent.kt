package com.vaibhav.presentation.username.choose_username

sealed class UserNameValidationEvent {

    data class Success(val userName: String): UserNameValidationEvent()
}
