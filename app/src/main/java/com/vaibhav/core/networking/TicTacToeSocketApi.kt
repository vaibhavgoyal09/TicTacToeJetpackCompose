package com.vaibhav.core.networking

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.vaibhav.core.models.ws.BaseModel
import kotlinx.coroutines.flow.Flow

interface TicTacToeSocketApi {

    @Receive
    fun observeEvents(): Flow<WebSocket.Event>

    @Send
    fun sendBaseModel(baseModel: BaseModel)

    @Receive
    fun receiveBaseModel(): Flow<BaseModel>

    companion object {
        fun create(scarlet: Scarlet): TicTacToeSocketApi {
            return scarlet.create()
        }
    }
}