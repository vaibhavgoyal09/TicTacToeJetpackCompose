package com.vaibhav.core.networking

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse

interface TicTacToeHttpApi {

    suspend fun getRooms(searchQuery: String): List<Room>

    suspend fun createRoom(request: CreateRoomRequest): BasicApiResponse

    suspend fun joinRoom(userName: String, roomName: String): BasicApiResponse
}