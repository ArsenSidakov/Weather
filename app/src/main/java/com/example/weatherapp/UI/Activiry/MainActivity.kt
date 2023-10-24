package com.example.weatherapp.UI.Activiry

import android.Manifest
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.Repository.Adapters.ListenerAdapter
import com.example.weatherapp.Repository.Adapters.WeatherDayAdapter
import com.example.weatherapp.Repository.Extencion.isPermissionGranted
import com.example.weatherapp.Repository.Retrofit.RecyclerViewModel
import com.example.weatherapp.UI.Interface.InitRecyclerView
import com.example.weatherapp.ViewModel.MainViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ListenerAdapter, InitRecyclerView {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherDayAdapter
    private lateinit var launcherPermission: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        checkPermission()
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.initListWeatherModel("Vladikavkaz")
        }
        mainViewModel.liveDataWeatherModel.observe(this) {
            binding.cityTextView.text = it.location.name
            binding.countryTextView.text = it.location.country
            binding.regionTextView.text = it.location.region
            binding.timeTextView.text = it.location.localtime
            binding.tempTextView.text = it.current.temp_c
            binding.fillingTempTextView.text =
                getString(R.string.feels_like) + it.current.feelslike_c
            binding.conditionTextView.text = it.current.condition.text
            binding.humidityTextView.text = getString(R.string.humidity) + it.current.humidity
            binding.windSpeedTextView.text = getString(R.string.wind_speed) + it.current.wind_kph
            binding.lastUpdateTextView.text =
                getString(R.string.last_Update) + it.current.last_updated.substringAfter(" ")
            Picasso.get().load("https:" + it.current.condition.icon).into(binding.imWeatherLive)
            CoroutineScope(Dispatchers.IO).launch {
                recyclerViewInit(mainViewModel.initDayList(it))
            }
        }

    }

    override fun recyclerViewInit(list: List<RecyclerViewModel>) {
        binding.recyclerViewWeather.layoutManager = LinearLayoutManager(this)
        adapter = WeatherDayAdapter(this)
        binding.recyclerViewWeather.adapter = adapter
        adapter.submitList(list)
    }

    override fun onClick(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val keyIntent = this@MainActivity.getString(R.string.keyIntent)
            val initArrayListHours = ArrayList(
                mainViewModel.initHoursList(
                    position,
                    mainViewModel.liveDataWeatherModel.value!!
                )
            )
            val arrayList = initArrayListHours
            runOnUiThread {
                startActivity(Intent(this@MainActivity, HoursActivity::class.java).apply {
                    putExtra(keyIntent, arrayList)
                })
            }
        }
    }


    private fun permissionListener() {
        launcherPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText( this,"Permission $it",Toast.LENGTH_SHORT).show()
        }
    }
    fun checkPermission(){
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            launcherPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}

