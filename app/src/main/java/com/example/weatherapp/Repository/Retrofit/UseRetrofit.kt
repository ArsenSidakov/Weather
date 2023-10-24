package com.example.weatherapp.Repository.Retrofit

import android.util.Log
import com.example.weatherapp.R
import com.example.weatherapp.Repository.Application.App
import com.example.weatherapp.Repository.Retrofit.DataClasses.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UseRetrofit {
    val app= App.applicationContext
    val keyRetrofit= app.getString(R.string.keyRetrofit)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherItems = retrofit.create(WeatherApi::class.java)
   suspend fun init(city:String):WeatherModel{
           val weatherModel = weatherItems.getWetherItem(
               keyRetrofit,
               city,
               "3" ,
               "no",
               "no"
           )
           return weatherModel
   }
}