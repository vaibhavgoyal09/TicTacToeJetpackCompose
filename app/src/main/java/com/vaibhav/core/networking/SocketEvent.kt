package com.vaibhav.core.networking

import io.ktor.http.cio.websocket.*

sealed class SocketEvent {

    object ConnectionOpened: SocketEvent()

    data class ConnectionClosed(val closeReason: CloseReason?): SocketEvent()
}
