package com.example.weatherapp.Repository.Retrofit.DataClasses



data class DayModel(
    val forecastday: List<ForecastDay>
)
data class ForecastDay(
    val date: String,
    val day: Day,
    val hour:List<HoursModel>
)
data class Day(
    val avgtemp_c: String,
    val mintemp_c: String,
    val maxtemp_c: String,
    val condition: ConditionDay,
    val daily_chance_of_rain:String
)

data class ConditionDay(
    val icon: String,
    val text: String
)
