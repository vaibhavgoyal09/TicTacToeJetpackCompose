package com.vaibhav.core.repository.abstraction

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse
import com.vaibhav.core.models.response.JoinRoomResponse
import com.vaibhav.util.ResponseResult

interface RoomsRepository {

    suspend fun getRooms(query: String): ResponseResult<List<Room>>

    suspend fun joinRoom(userName: String, roomName: String): ResponseResult<JoinRoomResponse>

    suspend fun createRoom(request: CreateRoomRequest): ResponseResult<BasicApiResponse>
}