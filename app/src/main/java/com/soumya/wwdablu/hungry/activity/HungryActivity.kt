package com.soumya.wwdablu.hungry.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.BuildConfig
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.repository.HungryRepo

abstract class HungryActivity : AppCompatActivity(), LocationListener {

    private var mLat: String = BuildConfig.DEFAULT_LATITUDE
    private var mLon: String = BuildConfig.DEFAULT_LOGITUDE

    private val mHandler: Handler = Handler()

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(this)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if(requestCode == 1001) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                locationManager?.requestLocationUpdates(
                        locationManager.getBestProvider(Criteria(), false) ?:
                        LocationManager.GPS_PROVIDER,
                        500, 1.0f, this)
            }

            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationChanged(location: Location) {
        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(this)

        mLat = location.latitude.toString() ?: BuildConfig.DEFAULT_LATITUDE
        mLon = location.longitude.toString() ?: BuildConfig.DEFAULT_LOGITUDE

        HungryRepo.setLocation(mLat, mLon)

        onLocationUpdated(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //
    }

    override fun onProviderEnabled(provider: String) {
        //
    }

    override fun onProviderDisabled(provider: String) {
        //
    }

    protected open fun onLocationUpdated(location: Location?) {
        //
    }

    protected fun postRunnableOnMain(r: Runnable, delayInMilli: Long = 0, cancelIfExists: Boolean = false) {
        if(cancelIfExists) {
            mHandler.removeCallbacks(r)
        }
        mHandler.postDelayed(r, delayInMilli)
    }

    protected fun fetchCurrentCoordinates() {

        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if(locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            val permissionResult = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)

            when (permissionResult) {
                PackageManager.PERMISSION_GRANTED -> {
                    locationManager.requestLocationUpdates(
                            locationManager.getBestProvider(Criteria(), false) ?:
                            LocationManager.GPS_PROVIDER,
                            500, 1.0f, this)
                }
                PackageManager.PERMISSION_DENIED -> {
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
                }
            }
        }
    }

    protected fun loadImageIntoImageView(imageUrl: String, imageView: ImageView) {

        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.default_card_bg)
            .into(imageView)
    }
}