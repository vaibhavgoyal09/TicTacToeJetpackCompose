package com.vaibhav.core.repository.implementation

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.util.safeApiCall
import com.vaibhav.util.DispatcherProvider
import com.vaibhav.util.ResponseResult
import retrofit2.HttpException
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor (
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

    override suspend fun joinRoom(
        userName: String,
        roomName: String
    ): ResponseResult<BasicApiResponse> {
        return safeApiCall(dispatcherProvider.io) {
            val response = ticTacToeApi.joinRoom(userName, roomName)
            val result = response.body()
            if (response.isSuccessful) {
                result ?: BasicApiResponse()
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun createRoom(request: CreateRoomRequest): ResponseResult<BasicApiResponse> {
        return safeApiCall(dispatcherProvider.io) {
            val response = ticTacToeApi.createRoom(request)
            val result = response.body()
            if (response.isSuccessful) {
                result ?: BasicApiResponse()
            } else {
                throw HttpException(response)
            }
        }
    }
}
