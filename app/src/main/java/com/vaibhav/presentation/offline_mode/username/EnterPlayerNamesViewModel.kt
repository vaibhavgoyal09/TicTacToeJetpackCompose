package com.vaibhav.presentation.offline_mode.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.common.navigation.Screen
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.presentation.common.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterPlayerNamesViewModel @Inject constructor() : ViewModel() {

    private val _player1NameState = mutableStateOf(StandardTextFieldState())
    val player1NameState: State<StandardTextFieldState> = _player1NameState

    private val _player2NameState = mutableStateOf(StandardTextFieldState())
    val player2NameState: State<StandardTextFieldState> = _player2NameState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

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
                    val route: String = if (player1Name.isNotEmpty() && player2Name.isNotEmpty()) {
                        Screen.OfflineGameScreen.route + "?player1Name=$player1Name" + "?player2Name=$player2Name"
                    } else if (player1Name.isEmpty() && player2Name.isNotEmpty()) {
                        Screen.OfflineGameScreen.route + "?player2Name=$player2Name"
                    } else if (player1Name.isNotEmpty() && player2Name.isEmpty()) {
                        Screen.OfflineGameScreen.route + "?player1Name=$player1Name"
                    } else {
                        Screen.OfflineGameScreen.route
                    }

                    _uiEvent.emit(UiEvent.Navigate(route))
                }
            }
        }
    }
}