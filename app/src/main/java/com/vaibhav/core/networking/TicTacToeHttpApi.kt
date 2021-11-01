package com.vaibhav.core.networking

import com.vaibhav.core.models.Room
import com.vaibhav.core.models.request.CreateRoomRequest
import com.vaibhav.core.models.response.BasicApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TicTacToeHttpApi {

    @GET("room/search")
    suspend fun getRooms(
        @Query("searchQuery") query: String
    ): Response<List<Room>>

    @POST("room/create")
    suspend fun createRoom(
        @Body request: CreateRoomRequest
    ): Response<BasicApiResponse>

    @GET("room/join")
    suspend fun joinRoom(
        @Query("userName") userName: String,
        @Query("roomName") roomName: String
    ): Response<BasicApiResponse>

    companion object {
        fun create(retrofit: Retrofit): TicTacToeHttpApi {
            return retrofit.create(TicTacToeHttpApi::class.java)
        }
    }
}