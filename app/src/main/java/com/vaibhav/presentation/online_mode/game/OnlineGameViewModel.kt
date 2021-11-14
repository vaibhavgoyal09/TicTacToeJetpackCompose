package com.vaibhav.presentation.online_mode.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.core.models.ws.*
import com.vaibhav.core.networking.SocketEvent
import com.vaibhav.core.networking.TicTacToeSocketApi
import com.vaibhav.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineGameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val clientId: String,
    private val socketApi: TicTacToeSocketApi,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<OnlineGameUiEvent>()
    val uiEvent: SharedFlow<OnlineGameUiEvent> = _uiEvent

    private val _player1NameState = mutableStateOf("")
    val player1NameState: State<String> = _player1NameState

    private val _player2NameState = mutableStateOf("")
    val player2NameState: State<String> = _player2NameState

    private val _player1ScoreState = mutableStateOf(0)
    val player1ScoreState: State<Int> = _player1ScoreState

    private val _player2ScoreState = mutableStateOf(0)
    val player2ScoreState: State<Int> = _player2ScoreState

    init {
        _player1NameState.value = savedStateHandle.get<String>("userName") ?: ""
        _player2NameState.value = savedStateHandle.get<String>("existingPlayerUserName") ?: ""
        observeSocketEvents()
        observeSocketMessages()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            socketApi.socketEvents.collect { event ->
                when (event) {
                    is SocketEvent.ConnectionOpened -> {
                        Log.d("Online Game view model", "web socket connection opened")
                        val joinRoom = JoinRoom(
                            savedStateHandle.get<String>("userName")!!,
                            savedStateHandle.get<String>("roomName")!!,
                            clientId
                        )
                        sendBaseModel(joinRoom)
                    }
                    is SocketEvent.ConnectionClosed -> {
                        Log.d("Online Game view model", "web socket connection closed")
                    }
                    is SocketEvent.RetryingToConnect -> {
                        println("Retrying to connect to web sockets")
                    }
                }
            }
        }
    }

    private fun observeSocketMessages() {
        viewModelScope.launch {
            socketApi.baseModelFlow.collect { baseModel ->
                when (baseModel) {
                    is Announcement -> {
                        when (baseModel.announcementType) {
                            Announcement.TYPE_PLAYER_JOINED -> {
                                _player2NameState.value = baseModel.playerUserName ?: ""
                                _uiEvent.emit(OnlineGameUiEvent.ShowSnackBar("${baseModel.playerUserName} joined the game."))
                            }
                            Announcement.TYPE_PLAYER_LEFT -> {
                                _uiEvent.emit(OnlineGameUiEvent.ShowSnackBar("${baseModel.playerUserName} left the game."))
                            }
                            Announcement.TYPE_PLAYER_WON -> {
                                if (baseModel.playerUserName == savedStateHandle.get<String>("userName")) {
                                    _uiEvent.emit(OnlineGameUiEvent.ShowWinDialog)
                                } else {
                                    _uiEvent.emit(OnlineGameUiEvent.ShowLostDialog)
                                }
                            }
                            Announcement.TYPE_MATCH_DRAW -> {
                                _uiEvent.emit(OnlineGameUiEvent.ShowMatchDrawDialog)
                            }
                        }
                    }
                    is GameError -> {
                        if (baseModel.errorType == GameError.TYPE_ROOM_NOT_FOUND) {
                            _uiEvent.emit(OnlineGameUiEvent.ShowSnackBar("No room was found."))
                            _uiEvent.emit(OnlineGameUiEvent.NavigateUp)
                            return@collect
                        }

                        if (baseModel.message != null) {
                            _uiEvent.emit(OnlineGameUiEvent.ShowSnackBar(baseModel.message))
                        }
                    }
                    is GamePhaseChange -> {

                    }
                    is StartGame -> {

                    }
                }
            }
        }
    }

    private fun sendBaseModel(baseModel: BaseModel) {
        viewModelScope.launch(dispatchers.io) {
            socketApi.sendMessage(baseModel)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sendBaseModel(DisconnectRequest())
    }
}