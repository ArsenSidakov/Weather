package com.example.weatherapp.Repository.Retrofit.DataClasses

data class HoursModel(
    val temp_c:String,
    val time:String,
    val condition: ConditionHours
)
data class ConditionHours(
    val icon:String,
    val text:String
)
