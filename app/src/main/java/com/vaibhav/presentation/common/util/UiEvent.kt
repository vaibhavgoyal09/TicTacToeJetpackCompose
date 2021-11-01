package com.vaibhav.presentation.common.util

sealed class UiEvent {

    data class ShowSnackBar(val message: String) : UiEvent()

    data class Navigate(val route: String) : UiEvent()

    object NavigateUp: UiEvent()
}
