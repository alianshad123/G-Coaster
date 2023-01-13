package com.anshad.g_coaster

import android.app.Application

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this


    }

    companion object {
        lateinit var instance: App
            private set
    }
}