package com.vaibhav.di

import com.squareup.moshi.Moshi
import com.vaibhav.core.networking.TicTacToeHttpApi
import com.vaibhav.core.repository.abstraction.RoomsRepository
import com.vaibhav.core.repository.implementation.RoomsRepositoryImpl
import com.vaibhav.util.Constants.TIC_TAC_TOE_HTTP_API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val coreModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
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
            .build()
    }

    single {
        Moshi.Builder().build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(TIC_TAC_TOE_HTTP_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        TicTacToeHttpApi.create(get())
    }

    factory<RoomsRepository> {
        RoomsRepositoryImpl(get(), get())
    }
}