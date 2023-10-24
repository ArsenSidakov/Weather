package com.example.weatherapp.Repository.Application

import android.content.Context

object App {
lateinit var applicationContext:Context

fun init(app: ApplicationApp){
    applicationContext=app.applicationContext
}
}