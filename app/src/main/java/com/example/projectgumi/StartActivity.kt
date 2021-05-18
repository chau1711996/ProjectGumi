package com.example.projectgumi

import android.app.Application
import com.example.projectgumi.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@StartActivity)
            modules(listOf(applicationModule))
        }
    }
}