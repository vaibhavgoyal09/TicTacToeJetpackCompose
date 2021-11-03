package com.vaibhav.presentation.online_mode.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.presentation.common.util.UserNameValidationErrors
import com.vaibhav.presentation.navigation.Screen
import com.vaibhav.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseUserNameViewModel @Inject constructor() : ViewModel() {

    private val _userNameFieldState = mutableStateOf(StandardTextFieldState())
    val userNameFieldState: State<StandardTextFieldState> = _userNameFieldState

    private val _userNameValidationEvent = MutableSharedFlow<ChooseUserNameUiEvent>()
    val userNameUiEvent: SharedFlow<ChooseUserNameUiEvent> = _userNameValidationEvent

    fun onEvent(eventChoose: ChooseUserNameEvent) {
        when (eventChoose) {
            is ChooseUserNameEvent.EnteredUserName -> {
                _userNameFieldState.value = _userNameFieldState.value.copy(
                    text = eventChoose.userName,
                    error = null
                )
            }
            is ChooseUserNameEvent.ValidateUserName -> {
                viewModelScope.launch {

                    val userName = _userNameFieldState.value.text

                    when {
                        userName.isEmpty() -> {
                            _userNameFieldState.value = _userNameFieldState.value.copy(
                                error = UserNameValidationErrors.Empty
                            )
                        }
                        userName.length < Constants.MIN_USERNAME_CHAR_COUNT -> {
                            _userNameFieldState.value = _userNameFieldState.value.copy(
                                error = UserNameValidationErrors.TooShort
                            )
                        }
                        else -> _userNameValidationEvent.emit(
                            ChooseUserNameUiEvent.Navigate(
                                Screen.SelectRoomScreen.route + "/$userName"
                            )
                        )
                    }
                }
            }
        }
    }
}