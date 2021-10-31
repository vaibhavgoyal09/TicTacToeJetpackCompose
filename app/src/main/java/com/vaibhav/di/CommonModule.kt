package com.vaibhav.di

import com.vaibhav.util.DispatcherProvider
import com.vaibhav.util.clientId
import com.vaibhav.util.datastore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {

    single {
        val context = androidContext()
        runBlocking {
            context.datastore.clientId()
        }
    }

    single<DispatcherProvider> {
        object : DispatcherProvider {

            override val main: CoroutineDispatcher
                get() = Dispatchers.Main

            override val io: CoroutineDispatcher
                get() = Dispatchers.IO

            override val default: CoroutineDispatcher
                get() = Dispatchers.Default

            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }
    }
}