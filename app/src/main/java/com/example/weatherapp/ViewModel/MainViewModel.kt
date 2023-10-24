package com.example.weatherapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Repository.Retrofit.DataClasses.WeatherModel
import com.example.weatherapp.Repository.Retrofit.UseRetrofit
import com.example.weatherapp.Repository.Retrofit.RecyclerViewModel

class MainViewModel : ViewModel(),SaveRetrofitModel {

    var liveDataWeatherModel= MutableLiveData<WeatherModel>()
    var liveDataWeatherModelList= MutableLiveData<List<WeatherModel>>()
    val useRetrofit = UseRetrofit()

    override suspend fun initListWeatherModel(city:String) {
       liveDataWeatherModel.postValue(useRetrofit.init(city))
    }

    override suspend fun initDayList(weatherModel: WeatherModel): List<RecyclerViewModel> {
         val listForecastDay = weatherModel.forecast.forecastday
        var listDay = ArrayList<RecyclerViewModel>()
        for (i in listForecastDay.indices) {
            val item = RecyclerViewModel(
                listForecastDay[i].date,
                false,
                "",
                listForecastDay[i].day.mintemp_c,
                listForecastDay[i].day.maxtemp_c,
                listForecastDay[i].day.condition.text,
                listForecastDay[i].day.condition.icon
            )
            listDay.add(item)
        }
        return listDay
    }

    override suspend fun initHoursList(position: Int,weatherModel: WeatherModel): ArrayList<RecyclerViewModel> {
        val listHours = weatherModel.forecast.forecastday[position].hour
        val listForecastDay = weatherModel.forecast.forecastday[position]
        var list = ArrayList<RecyclerViewModel>()
        for (i in listHours.indices) {
            val item = RecyclerViewModel(
                listHours[i].time,
                 true,
                listHours[i].temp_c ,
                listForecastDay.day.mintemp_c,
                listForecastDay.day.maxtemp_c,
                listHours[i].condition.text,
                listHours[i].condition.icon
            )
            list.add(item)
        }
        return list
    }

}