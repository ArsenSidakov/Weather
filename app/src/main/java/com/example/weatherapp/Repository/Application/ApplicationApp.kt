package com.example.weatherapp.Repository.Application

import android.app.Application

class ApplicationApp: Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(this)
    }
}