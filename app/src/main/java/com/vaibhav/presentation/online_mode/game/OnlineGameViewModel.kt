package com.vaibhav.presentation.online_mode.game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.core.models.ws.BaseModel
import com.vaibhav.core.models.ws.DisconnectRequest
import com.vaibhav.core.models.ws.JoinRoom
import com.vaibhav.core.networking.SocketEvent
import com.vaibhav.core.networking.TicTacToeSocketApi
import com.vaibhav.presentation.common.util.UiEvent
import com.vaibhav.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        observeSocketEvents()
        observeSocketMessages()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            println("Observe socket events")
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
            println("Observe socket messages")
            socketApi.baseModelFlow.collect { baseModel ->
                when (baseModel) {
                    is JoinRoom -> {

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