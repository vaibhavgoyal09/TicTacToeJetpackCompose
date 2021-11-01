package com.vaibhav.core.repository.implementation

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.util.safeApiCall
import com.vaibhav.util.DispatcherProvider
import com.vaibhav.util.ResponseResult
import retrofit2.HttpException

class RoomsRepositoryImpl(
    private val ticTacToeApi: TicTacToeHttpApi,
    private val dispatcherProvider: DispatcherProvider
) : RoomsRepository {

    override suspend fun getRooms(query: String): ResponseResult<List<Room>> {
        return safeApiCall(dispatcherProvider.io) {
            val response = ticTacToeApi.getRooms(query)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                result
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun joinRoom(userName: String, roomName: String): ResponseResult<Unit> {
        return safeApiCall(dispatcherProvider.io) {
            val response = ticTacToeApi.joinRoom(userName, roomName)
            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        }
    }

    override suspend fun createRoom(request: CreateRoomRequest): ResponseResult<Unit> {
        return safeApiCall(dispatcherProvider.io) {
            val response = ticTacToeApi.createRoom(request)
            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        }
    }
}
