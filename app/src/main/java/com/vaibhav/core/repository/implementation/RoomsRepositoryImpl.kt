package com.vaibhav.core.repository.implementation

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse
import com.vaibhav.core.models.response.JoinRoomResponse
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.util.safeApiCall
import com.vaibhav.util.DispatcherProvider
import com.vaibhav.util.ResponseResult
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val ticTacToeApi: TicTacToeHttpApi,
    private val dispatcherProvider: DispatcherProvider
) : RoomsRepository {

    override suspend fun getRooms(query: String): ResponseResult<List<Room>> {
        return safeApiCall(dispatcherProvider.io) {
            ticTacToeApi.getRooms(query)
        }
    }

    override suspend fun joinRoom(
        userName: String,
        roomName: String
    ): ResponseResult<JoinRoomResponse> {
        return safeApiCall(dispatcherProvider.io) {
            ticTacToeApi.joinRoom(userName, roomName)
        }
    }

    override suspend fun createRoom(request: CreateRoomRequest): ResponseResult<BasicApiResponse> {
        return safeApiCall(dispatcherProvider.io) {
            ticTacToeApi.createRoom(request)
        }
    }
}
