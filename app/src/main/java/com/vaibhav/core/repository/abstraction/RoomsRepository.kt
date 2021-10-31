package com.vaibhav.core.repository.abstraction

import com.vaibhav.core.models.Room
import com.vaibhav.util.ResponseResult

interface RoomsRepository {

    suspend fun getRooms(query: String): ResponseResult<List<Room>>

    suspend fun joinRoom(userName: String, roomName: String): ResponseResult<Unit>

    suspend fun createRoom(room: Room): ResponseResult<Unit>
}