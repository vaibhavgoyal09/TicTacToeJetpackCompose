package com.vaibhav.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.vaibhav.core.networking.CustomMoshiMessageAdapter
import com.vaibhav.core.networking.FlowStreamAdapter
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.networking.TicTacToeSocketApi
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.repository.implementation.RoomsRepositoryImpl
import com.vaibhav.util.Constants.SOCKET_CONNECT_RETRY_INTERVAL
import com.vaibhav.util.Constants.TIC_TAC_TOE_HTTP_API_BASE_URL
import com.vaibhav.util.Constants.TIC_TAC_TOE_SOCKET_API_URL
import com.vaibhav.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        clientId: String
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("client_id", clientId)
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        moshi: Moshi
    ): Retrofit {
        val okHttpClient = okHttpClientBuilder
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TIC_TAC_TOE_HTTP_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providesTicTacToeHttpApi(retrofit: Retrofit): TicTacToeHttpApi {
        return TicTacToeHttpApi.create(retrofit)
    }

    @Singleton
    @Provides
    fun provideScarlet(
        okHttpClientBuilder: OkHttpClient.Builder,
        moshi: Moshi,
        app: Application
    ): Scarlet {

        val okHttpClient = okHttpClientBuilder.build()

        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomMoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .backoffStrategy(LinearBackoffStrategy(SOCKET_CONNECT_RETRY_INTERVAL))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .webSocketFactory(okHttpClient.newWebSocketFactory(TIC_TAC_TOE_SOCKET_API_URL))
            .build()
    }

    @Provides
    @Singleton
    fun provideTicTacToeSocketApi(
        scarlet: Scarlet
    ): TicTacToeSocketApi {
        return TicTacToeSocketApi.create(scarlet)
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