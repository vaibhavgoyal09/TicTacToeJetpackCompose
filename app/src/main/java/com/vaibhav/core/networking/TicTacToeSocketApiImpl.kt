package com.vaibhav.core.networking

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.vaibhav.core.models.ws.BaseModel
import com.vaibhav.core.models.ws.JoinRoom
import com.vaibhav.util.Constants.TIC_TAC_TOE_SOCKET_API_URL
import com.vaibhav.util.Constants.TYPE_JOIN_ROOM
import com.vaibhav.util.DispatcherProvider
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class TicTacToeSocketApiImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val client: HttpClient,
    private val gson: Gson
) : TicTacToeSocketApi {

    private val _socketEventChannel: Channel<SocketEvent> = Channel()

    private val _baseModelChannel: Channel<BaseModel> = Channel()

    private var webSocketSession: DefaultClientWebSocketSession? = null

    private lateinit var socketConnectJob: CompletableJob

    private val socketConnectRetryJob: Job = Job()

    private val socketExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleSocketError(exception)
    }

    private val childExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleSocketError(exception)
    }

    override val socketEvents: Flow<SocketEvent>
        get() = _socketEventChannel.receiveAsFlow().flowOn(dispatchers.io)

    override val baseModelFlow: Flow<BaseModel>
        get() = _baseModelChannel.receiveAsFlow().flowOn(dispatchers.io)

    init {
        openConnection()
    }

    private fun openConnection() {
        socketConnectJob = Job()
        CoroutineScope(dispatchers.io + socketConnectJob).launch(socketExceptionHandler) {
            supervisorScope {
                println("trying to connect to web socket connection")
                client.webSocket(urlString = TIC_TAC_TOE_SOCKET_API_URL) {
                    webSocketSession = this

                    _socketEventChannel.send(SocketEvent.ConnectionOpened)

                    val messagesReceivedRoutine = launch(childExceptionHandler) {
                        observeSocketMessages()
                    }
                    messagesReceivedRoutine.join()
                }
            }
        }
    }

    override suspend fun sendMessage(baseModel: BaseModel) {
        val message = gson.toJson(baseModel)
        Log.e("TicTacToeSocketApiImpl", message)
        try {
            webSocketSession?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
            socketConnectJob.cancel(CancellationException(cause = e))
        }
    }

    private fun handleSocketError(throwable: Throwable) {
        Log.e("TicTacToeSocketApiImpl", throwable.localizedMessage ?: "Error")
        CoroutineScope(dispatchers.io).launch {
            _socketEventChannel.send(SocketEvent.ConnectionClosed(null))
        }
        retryToConnect()
    }

    private fun retryToConnect() {
        CoroutineScope(dispatchers.io + socketConnectRetryJob).launch {
            delay(SOCKET_CONNECT_RETRY_INTERVAL)
            _socketEventChannel.send(SocketEvent.RetryingToConnect)
            openConnection()
        }
    }

    private suspend fun observeSocketMessages() {
        webSocketSession?.incoming?.consumeEach { frame ->
            if (frame is Frame.Text) {
                val message = frame.readText()
                val jsonObject = JsonParser.parseString(message).asJsonObject

                val type = when (jsonObject.get("type").asString) {
                    TYPE_JOIN_ROOM -> JoinRoom::class.java
                    else -> BaseModel::class.java
                }

                val payload: BaseModel = gson.fromJson(message, type)
                _baseModelChannel.send(payload)
            }
        }
    }

    companion object {
        private const val SOCKET_CONNECT_RETRY_INTERVAL = 3000L // 3 Seconds
    }
}