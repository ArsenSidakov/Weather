package com.example.weatherapp.UI.Activiry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.Repository.Adapters.ListenerAdapter
import com.example.weatherapp.Repository.Adapters.WeatherDayAdapter
import com.example.weatherapp.Repository.Retrofit.RecyclerViewModel
import com.example.weatherapp.UI.Interface.InitRecyclerView
import com.example.weatherapp.databinding.ActivityHoursBinding

class HoursActivity : AppCompatActivity(), ListenerAdapter, InitRecyclerView {
    lateinit var binding: ActivityHoursBinding
    private lateinit var adapter: WeatherDayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoursBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val key =getString(R.string.keyIntent)
        val hourResult = intent.getSerializableExtra(key) as List<RecyclerViewModel>
        recyclerViewInit(hourResult)
        binding.timeViewHour.text = hourResult[1].date.substringBefore(" ")
        binding.conditionViewHour.text =" ${hourResult[1].mintemp}°C/${hourResult[1].maxtemp}°C"
    }

    override fun recyclerViewInit(list: List<RecyclerViewModel>) {
        binding.recyclerViewHours.layoutManager = LinearLayoutManager(this)
        adapter = WeatherDayAdapter(this)
        binding.recyclerViewHours.adapter = adapter
        adapter.submitList(list)
    }

    override fun onClick(position: Int) {
    }
}