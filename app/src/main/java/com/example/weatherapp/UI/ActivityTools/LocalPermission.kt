package com.example.weatherapp.UI.ActivityTools

interface LocalPermission {
    fun locationEnabled():Boolean

    fun getLocation()

    fun permissionListener()

    fun checkPermission()

    fun checkLocation()

}