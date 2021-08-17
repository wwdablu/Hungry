package com.soumya.wwdablu.hungry.activity.common

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.soumya.wwdablu.hungry.BuildConfig
import com.soumya.wwdablu.hungry.repository.HungryRepo

abstract class LocationProviderActivity : HungryActivity() {

    private var mLat: String = BuildConfig.DEFAULT_LATITUDE
    private var mLon: String = BuildConfig.DEFAULT_LOGITUDE

    private lateinit var mLocationProvider: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest

    protected abstract fun onLocationUpdated(location: Location)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest.interval = 500
        mLocationRequest.fastestInterval = 100
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if(requestCode == 1001) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation()
            }

            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    protected fun fetchCurrentLocation() {

        val permissionResult = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)

        when (permissionResult) {
            PackageManager.PERMISSION_GRANTED -> {

                mLocationProvider.lastLocation.addOnSuccessListener {
                    if(it != null) {
                        onLocationChanged(it)
                    }
                }
            }
            PackageManager.PERMISSION_DENIED -> {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
            else -> {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
        }
    }

    private fun onLocationChanged(location: Location) {


        mLat = location.latitude.toString() ?: BuildConfig.DEFAULT_LATITUDE
        mLon = location.longitude.toString() ?: BuildConfig.DEFAULT_LOGITUDE

        HungryRepo.setLocation(mLat, mLon)

        onLocationUpdated(location)
    }
}