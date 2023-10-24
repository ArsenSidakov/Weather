package com.example.weatherapp.ViewModel

import com.example.weatherapp.Repository.Retrofit.DataClasses.WeatherModel
import com.example.weatherapp.Repository.Retrofit.RecyclerViewModel

interface SaveRetrofitModel {
    suspend fun initListWeatherModel(city:String)

    suspend fun initDayList(weatherModel: WeatherModel):List<RecyclerViewModel>

    suspend fun initHoursList(position: Int,weatherModel: WeatherModel): ArrayList<RecyclerViewModel>
}