package com.example.weatherapp.Repository.Retrofit.DataClasses

data class WeatherModel(
    val location: Location,
    val current: Current,
    val forecast: DayModel
)
data class Location(
    val region:String,
    val country:String,
    val name: String,
    val localtime:String
)
data class Current(
    val last_updated:String,
    val temp_c:String,
    val condition: Condition,
    val humidity:String,
    val wind_kph:String,
    val feelslike_c:String
)
data class Condition(
    val text:String,
    val icon:String
)

