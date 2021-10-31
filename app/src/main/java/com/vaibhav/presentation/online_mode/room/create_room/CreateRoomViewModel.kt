package com.vaibhav.presentation.online_mode.room.create_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.presentation.common.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CreateRoomViewModel(
    private val roomsRepository: RoomsRepository
): ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _textFieldState = mutableStateOf(StandardTextFieldState())
    val textFieldState: State<StandardTextFieldState> = _textFieldState

    fun onEvent(event: CreateRoomInputEvents) {
        when(event) {
            is CreateRoomInputEvents.EnteredRoomName -> {
                _textFieldState.value = _textFieldState.value.copy(
                    text = event.name,
                    error = null
                )
            }
        }
    }
}