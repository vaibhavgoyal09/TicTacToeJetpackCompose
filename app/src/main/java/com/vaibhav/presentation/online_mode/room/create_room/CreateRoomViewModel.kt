package com.vaibhav.presentation.online_mode.room.create_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository,
    private val savedStateHandle: SavedStateHandle
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

                    val isValidRoomName: Boolean = when {
                        roomName.isEmpty() -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Empty
                            )
                            false
                        }
                        roomName.length < MIN_ROOM_NAME_CHAR_COUNT -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Short
                            )
                            false
                        }
                        roomName.length > MAX_ROOM_NAME_CHAR_COUNT -> {
                            _textFieldState.value = _textFieldState.value.copy(
                                error = RoomNameValidationErrors.Long
                            )
                            false
                        }
                        else -> true
                    }

                    if (isValidRoomName) {
                        val isRoomCreated = createRoom(roomName)
                        val userName = savedStateHandle.get<String>("userName") ?: ""
                        if (isRoomCreated) {
                            joinRoom(userName, roomName)
                        }
                    }
                }
            }
        }
    }

    private suspend fun createRoom(roomName: String): Boolean {
        val request = CreateRoomRequest(roomName)

        return when (val result = roomsRepository.createRoom(request)) {
            is ResponseResult.Success -> {
                if (result.data!!.isSuccessful) {
                    true
                } else {
                    showSnackBar(result.data.message ?: "Error while creating room.")
                    false
                }
            }
            is ResponseResult.Error -> {
                showSnackBar("Error while creating room.")
                false
            }
        }
    }

    private suspend fun joinRoom(userName: String, roomName: String) {
        when (val result = roomsRepository.joinRoom(userName, roomName)) {
            is ResponseResult.Success -> {
                if (result.data!!.isSuccessful) {
                    _uiEvent.emit(UiEvent.Navigate(Screen.OnlineGameScreen.route + "/$roomName" + "/$userName"))
                } else {
                    showSnackBar(result.data.message ?: "Error while joining room.")
                }
            }
            is ResponseResult.Error -> {
                showSnackBar("Error while joining room.")
            }
        }
    }

    private suspend fun showSnackBar(message: String) {
        _uiEvent.emit(UiEvent.ShowSnackBar(message))
    }
}