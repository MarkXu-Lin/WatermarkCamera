package com.mark.watermarkcamera

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
    }
    companion object{
        private lateinit var app: MainApplication
        fun getApp() = app
    }
}