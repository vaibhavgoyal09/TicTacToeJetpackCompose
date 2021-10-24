package com.vaibhav

import android.app.Application
import com.vaibhav.di.coreModule
import com.vaibhav.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    coreModule,
                    presentationModule
                )
            )
        }
    }
}