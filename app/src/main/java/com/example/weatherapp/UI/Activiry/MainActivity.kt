package com.example.weatherapp.UI.Activiry

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
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
import com.example.weatherapp.UI.ActivityTools.DialogManager
import com.example.weatherapp.UI.ActivityTools.InitRecyclerView
import com.example.weatherapp.UI.ActivityTools.LocalPermission
import com.example.weatherapp.ViewModel.MainViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ListenerAdapter, InitRecyclerView, LocalPermission {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherDayAdapter
    private lateinit var launcherPermission: ActivityResultLauncher<String>
    lateinit var location: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        location = LocationServices.getFusedLocationProviderClient(this)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        checkPermission()
        checkLocation()
        getLocation()
        binding.imSearchCity.setOnClickListener {
            DialogManager.cityTake(this, object : DialogManager.DialogListener {
                override fun onClick(name: String?) {
                    CoroutineScope(Dispatchers.IO).launch {
                        name?.let { it1 -> mainViewModel.initListWeatherModel(it1) }
                    }
                }
            })
            binding.imUpdate.setOnClickListener {
                getLocation()
            }
        }
        mainViewModel.liveDataWeatherModel.observe(this) {
            binding.cityTextView.text = it.location.name
            binding.countryTextView.text = it.location.country
            binding.regionTextView.text = it.location.region.substringAfter(",")
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

    override fun onResume() {
        super.onResume()
        checkLocation()
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

    override fun checkLocation() {
        if (locationEnabled()) {
        } else {
            DialogManager.locationSetting(this, object : DialogManager.DialogListener {
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
        }
    }

    override fun locationEnabled(): Boolean {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun getLocation() {
        if (!locationEnabled()) {
            Toast.makeText(this, getString(R.string.location_ON), Toast.LENGTH_SHORT).show()
            return
        }
        val cancellationToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        location.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token)
            .addOnCompleteListener {
                CoroutineScope(Dispatchers.Main).launch {
                    mainViewModel.initListWeatherModel("${it.result.latitude},${it.result.longitude}")
                }
            }

    }

    override fun permissionListener() {
        launcherPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Toast.makeText(this, getString(R.string.permission_ON), Toast.LENGTH_SHORT).show()
            }
    }

    override fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            launcherPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}

