package com.example.weatherapp.Repository.Retrofit

import java.io.Serializable


data class RecyclerViewModel(
    val date: String,
    val flagHoursOrDay:Boolean,
    val temp: String,
    val mintemp: String,
    val maxtemp: String,
    val condition: String,
    val icon: String,
):Serializable



