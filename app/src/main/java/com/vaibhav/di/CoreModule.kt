package com.vaibhav.di

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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val coreModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("client_id", get())
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

    single {
        Moshi.Builder().build()
    }

    single {

        val okHttpClientBuilder = get<OkHttpClient.Builder>()
        val okHttpClient = okHttpClientBuilder
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TIC_TAC_TOE_HTTP_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        TicTacToeHttpApi.create(get())
    }

    single {

        val okHttpClientBuilder = get<OkHttpClient.Builder>()
        val okHttpClient = okHttpClientBuilder.build()

        Scarlet.Builder()
            .addMessageAdapterFactory(CustomMoshiMessageAdapter.Factory(get()))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .backoffStrategy(LinearBackoffStrategy(SOCKET_CONNECT_RETRY_INTERVAL))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(androidApplication()))
            .webSocketFactory(okHttpClient.newWebSocketFactory(TIC_TAC_TOE_SOCKET_API_URL))
            .build()
    }

    single {
        TicTacToeSocketApi.create(get())
    }

    factory<RoomsRepository> {
        RoomsRepositoryImpl(get(), get())
    }
}