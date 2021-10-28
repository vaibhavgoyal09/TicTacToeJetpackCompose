package com.vaibhav.presentation.online_mode.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.presentation.common.util.UserNameValidationErrors
import com.vaibhav.util.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ChooseUserNameViewModel: ViewModel() {

    private val _userNameFieldState = mutableStateOf(StandardTextFieldState())
    val userNameFieldState: State<StandardTextFieldState> = _userNameFieldState

    private val _userNameValidationEvent = MutableSharedFlow<ChooseUserNameValidationEvent>()
    val userNameValidationEvent: SharedFlow<ChooseUserNameValidationEvent> = _userNameValidationEvent

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
                            ChooseUserNameValidationEvent.Success(_userNameFieldState.value.text)
                        )
                    }
                }
            }
        }
    }
}