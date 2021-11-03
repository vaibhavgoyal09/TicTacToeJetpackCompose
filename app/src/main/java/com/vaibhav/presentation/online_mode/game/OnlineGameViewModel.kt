package com.vaibhav.presentation.online_mode.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import com.vaibhav.core.models.ws.BaseModel
import com.vaibhav.core.models.ws.JoinRoom
import com.vaibhav.core.networking.TicTacToeSocketApi
import com.vaibhav.presentation.common.util.UiEvent
import com.vaibhav.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineGameViewModel @Inject constructor (
    private val gameApi: TicTacToeSocketApi,
    private val dispatcherProvider: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun observeSocketEvents(userName: String, roomName: String) {
        viewModelScope.launch {
            gameApi.observeEvents().collectLatest { event ->
                when(event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {

                    }
                    is WebSocket.Event.OnConnectionClosed -> {

                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        _uiEvent.emit(UiEvent.ShowSnackBar("Couldn't connect to the servers."))
                    }
                }
            }
        }
    }

    private fun sendBaseModel(baseModel: BaseModel) {
        viewModelScope.launch(dispatcherProvider.io) {
            gameApi.sendBaseModel(baseModel)
        }
    }
}