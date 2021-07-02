package com.gumi.projectgumi

import android.app.Application
import com.gumi.projectgumi.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@StartApplication)
            modules(listOf(applicationModule))
        }
    }
}