package com.vaibhav.presentation.online_mode.username

sealed class ChooseUserNameUiEvent {

    data class Navigate(val route: String): ChooseUserNameUiEvent()
}
