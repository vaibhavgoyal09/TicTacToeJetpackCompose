package com.vaibhav.presentation.common.util

sealed class UiEvent {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
}
