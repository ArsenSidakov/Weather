package com.example.weatherapp.Repository.Retrofit

import com.example.weatherapp.Repository.Retrofit.DataClasses.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWetherItem(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherModel

}