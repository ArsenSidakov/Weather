package com.example.weatherapp.Repository.Extencion

import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weatherapp.Repository.Application.App
import com.example.weatherapp.ViewModel.MainViewModel

val app = App.applicationContext
fun AppCompatActivity.isPermissionGranted(permission: String):Boolean{
    return ContextCompat.checkSelfPermission(
        this,
        permission) == PackageManager.PERMISSION_GRANTED
}