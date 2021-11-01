package com.vaibhav.presentation.online_mode.room.create_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.presentation.common.util.UiEvent
import com.vaibhav.presentation.navigation.Screen
import com.vaibhav.util.Constants.MAX_ROOM_NAME_CHAR_COUNT
import com.vaibhav.util.Constants.MIN_ROOM_NAME_CHAR_COUNT
import com.vaibhav.util.ResponseResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CreateRoomViewModel(
    private val roomsRepository: RoomsRepository
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _textFieldState = mutableStateOf(StandardTextFieldState())
    val textFieldState: State<StandardTextFieldState> = _textFieldState

    fun onEvent(event: CreateRoomInputEvents) {
        when (event) {
            is CreateRoomInputEvents.EnteredRoomName -> {
                _textFieldState.value = _textFieldState.value.copy(
                    text = event.name,
                    error = null
                )
            }
            is CreateRoomInputEvents.CreateRoom -> {
                viewModelScope.launch {

                    val roomName = _textFieldState.value.text

                    when {
                        roomName.isEmpty() -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Empty
                            )
                        }
                        roomName.length < MIN_ROOM_NAME_CHAR_COUNT -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Short
                            )
                        }
                        roomName.length > MAX_ROOM_NAME_CHAR_COUNT -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Long
                            )
                        }
                        else -> createRoom(roomName)
                    }
                }
            }
        }
    }

    private suspend fun createRoom(roomName: String) {
        val request = CreateRoomRequest(roomName)

        when (val result = roomsRepository.createRoom(request)) {
            is ResponseResult.Success -> {
                _uiEvent.emit(UiEvent.Navigate(Screen.OnlineGameScreen.route + "/$roomName"))
            }
            is ResponseResult.Error -> {
                _uiEvent.emit(UiEvent.ShowSnackBar(result.error!!))
            }
        }
    }
}