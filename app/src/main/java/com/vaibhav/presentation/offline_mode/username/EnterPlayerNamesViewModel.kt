package com.vaibhav.presentation.offline_mode.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.common.util.StandardTextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class EnterPlayerNamesViewModel : ViewModel() {

    private val _player1NameState = mutableStateOf(StandardTextFieldState())
    val player1NameState: State<StandardTextFieldState> = _player1NameState

    private val _player2NameState = mutableStateOf(StandardTextFieldState())
    val player2NameState: State<StandardTextFieldState> = _player2NameState

    private val _userNameValidationEvent = MutableSharedFlow<EnterPlayerNamesValidationEvent>()
    val userNameValidationEvent: SharedFlow<EnterPlayerNamesValidationEvent> = _userNameValidationEvent

    fun onEvent(event: EnterPlayerNamesEvent) {
        when (event) {
            is EnterPlayerNamesEvent.EnteredPlayer1Name -> {
                _player1NameState.value = _player1NameState.value.copy(
                    text = event.name
                )
            }
            is EnterPlayerNamesEvent.EnteredPlayer2Name -> {
                _player2NameState.value = _player2NameState.value.copy(
                    text = event.name
                )
            }
            is EnterPlayerNamesEvent.ValidateUserNames -> {
                val player1Name = _player1NameState.value.text
                val player2Name = _player2NameState.value.text

                viewModelScope.launch {
                    _userNameValidationEvent.emit(
                        EnterPlayerNamesValidationEvent.Success(
                            player1Name,
                            player2Name
                        )
                    )
                }
            }
        }
    }
}