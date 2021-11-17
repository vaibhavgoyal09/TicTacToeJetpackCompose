package com.vaibhav.di

import com.google.gson.Gson
import com.vaibhav.core.networking.TicTacToeSocketApi
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.networking.TicTacToeHttpApiImpl
import com.vaibhav.core.networking.TicTacToeSocketApiImpl
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.repository.implementation.RoomsRepositoryImpl
import com.vaibhav.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        okHttpClient: OkHttpClient,
        clientId: String
    ): HttpClient {
        return HttpClient(OkHttp) {

            defaultRequest {
                parameter("clientId", clientId)
            }

            engine {
                preconfigured = okHttpClient
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(JsonFeature) {
                serializer = GsonSerializer()
            }

            install(WebSockets) {

            }
        }
    }

    @Singleton
    @Provides
    fun providesTicTacToeHttpApi(
        client: HttpClient
    ): TicTacToeHttpApi {
        return TicTacToeHttpApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideTicTacToeSocketApi(
        client: HttpClient,
        dispatcherProvider: DispatcherProvider,
        gson: Gson
    ): TicTacToeSocketApi {
        return TicTacToeSocketApiImpl(dispatcherProvider, client, gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(
        ticTacToeHttpApi: TicTacToeHttpApi,
        dispatcherProvider: DispatcherProvider
    ): RoomsRepository {
        return RoomsRepositoryImpl(ticTacToeHttpApi, dispatcherProvider)
    }
}