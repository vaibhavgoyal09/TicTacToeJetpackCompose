package com.vaibhav.presentation.online_mode.username

sealed class ChooseUserNameValidationEvent {

    data class Success(val userName: String): ChooseUserNameValidationEvent()
}
