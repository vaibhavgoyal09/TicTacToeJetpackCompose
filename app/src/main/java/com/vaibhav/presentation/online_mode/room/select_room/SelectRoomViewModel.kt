package com.vaibhav.presentation.online_mode.room.select_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.presentation.common.navigation.Screen
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.util.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRoomViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _roomsEventState = mutableStateOf(RoomsEventState())
    val roomsEventState: State<RoomsEventState> = _roomsEventState

    private val _selectRoomEvent = MutableSharedFlow<SelectRoomOutputEvent>()
    val selectRoomEvent = _selectRoomEvent.asSharedFlow()

    private var searchJob: Job? = null

    init {
        onEvent(SelectRoomInputEvent.Search)
    }

    companion object {
        private const val SEARCH_DELAY = 500L
    }

    fun onEvent(inputEvent: SelectRoomInputEvent) {
        when (inputEvent) {
            is SelectRoomInputEvent.EnteredRoomName -> {
                _searchFieldState.value = _searchFieldState.value.copy(
                    text = inputEvent.name
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DELAY)
                    searchRooms()
                }
            }
            is SelectRoomInputEvent.Search -> {
                searchRooms()
            }
            is SelectRoomInputEvent.Refresh -> {
                refreshRooms()
            }
            is SelectRoomInputEvent.CreateNewRoom -> {
                viewModelScope.launch {
                    _selectRoomEvent.emit(
                        SelectRoomOutputEvent.Navigate(
                            Screen.CreateNewRoomScreen.route + "/${
                                savedStateHandle.get<String>(
                                    "userName"
                                )
                            }"
                        )
                    )
                }
            }
            is SelectRoomInputEvent.JoinRoom -> {
                joinRoom(inputEvent.userName, inputEvent.roomName)
            }
        }
    }

    private fun joinRoom(userName: String, roomName: String) {
        viewModelScope.launch {
            when (val result = roomsRepository.joinRoom(userName, roomName)) {
                is ResponseResult.Success -> {
                    if (result.data!!.isSuccess) {

                        val route: String = if (result.data.existingPlayerUserName != null) {
                            Screen.OnlineGameScreen.route + "/$roomName" + "/$userName" + "?existingPlayerUserName=${result.data.existingPlayerUserName}"
                        } else {
                            Screen.OnlineGameScreen.route + "/$roomName" + "/$userName"
                        }
                        _selectRoomEvent.emit(
                            SelectRoomOutputEvent.Navigate(route)
                        )
                    } else {
                        showSnackBar(result.data.errorMessage ?: "Error while joining room.")
                    }
                }
                is ResponseResult.Error -> {
                    showSnackBar(result.error!!)
                }
            }
        }
    }

    private suspend fun showSnackBar(message: String) {
        _selectRoomEvent.emit(SelectRoomOutputEvent.ShowSnackBar(message))
    }

    private fun refreshRooms() {
        viewModelScope.launch {

            // Only show Loading status when there was an error before else just update the list
            if (_roomsEventState.value.error != null) {
                _roomsEventState.value = RoomsEventState(
                    isLoading = true
                )
            }

            val searchQuery = _searchFieldState.value.text
            when (val result = roomsRepository.getRooms(searchQuery)) {
                is ResponseResult.Success -> {
                    _roomsEventState.value = _roomsEventState.value.copy(
                        rooms = result.data,
                        error = null,
                        isLoading = false
                    )
                }
                is ResponseResult.Error -> {
                    _roomsEventState.value = RoomsEventState(
                        error = result.error
                    )
                }
            }
        }
    }

    private fun searchRooms() {
        viewModelScope.launch {

            _roomsEventState.value = RoomsEventState(
                isLoading = true
            )

            val searchQuery = _searchFieldState.value.text

            when (val result = roomsRepository.getRooms(searchQuery)) {
                is ResponseResult.Success -> {
                    _roomsEventState.value = RoomsEventState(
                        rooms = result.data
                    )
                }
                is ResponseResult.Error -> {
                    _roomsEventState.value = RoomsEventState(
                        error = result.error
                    )
                }
            }
        }
    }
}