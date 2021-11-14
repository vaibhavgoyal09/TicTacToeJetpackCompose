package com.vaibhav.core.networking

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse
import com.vaibhav.core.models.response.JoinRoomResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class TicTacToeHttpApiImpl @Inject constructor(
    private val client: HttpClient
) : TicTacToeHttpApi {

    override suspend fun getRooms(searchQuery: String): List<Room> {
        return client.get {
            url(HttpRoutes.SEARCH_ROOM_ROUTE)
            parameter("searchQuery", searchQuery)
        }
    }

    override suspend fun createRoom(request: CreateRoomRequest): BasicApiResponse {
        return client.post {
            url(HttpRoutes.CREATE_ROOM_ROUTE)
            body = request
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun joinRoom(userName: String, roomName: String): JoinRoomResponse {
        return client.get {
            url(HttpRoutes.JOIN_ROOM_ROUTE)
            parameter("userName", userName)
            parameter("roomName", roomName)
        }
    }
}