package com.vaibhav.core.networking

import com.vaibhav.core.models.ws.BaseModel
import kotlinx.coroutines.flow.Flow

interface TicTacToeSocketApi {

    val socketEvents: Flow<SocketEvent>

    val baseModelFlow: Flow<BaseModel>

    suspend fun sendMessage(baseModel: BaseModel)
}