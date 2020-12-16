package com.soumya.wwdablu.hungry.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.soumya.wwdablu.hungry.BuildConfig
import com.soumya.wwdablu.hungry.databinding.ActivityLocationAccessPermissionBinding
import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LocationAccessPermission : AppCompatActivity(), LocationListener {

    private lateinit var mViewBinding: ActivityLocationAccessPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLocationAccessPermissionBinding.inflate(layoutInflater)

        mViewBinding.btnAutoLocation.setOnClickListener {
            fetchCurrentCoordinates()
        }

        setContentView(mViewBinding.root)
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
                        locationManager?.getBestProvider(Criteria(), false),
                        500, 1.0f, this)
            }

            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun fetchCurrentCoordinates() {

        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if(locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            val permissionResult = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)

            when (permissionResult) {
                PackageManager.PERMISSION_GRANTED -> {
                    locationManager?.requestLocationUpdates(
                            locationManager?.getBestProvider(Criteria(), false),
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

    override fun onLocationChanged(location: Location?) {

        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(this)

        val lat: String = location?.latitude?.toString() ?: BuildConfig.DEFAULT_LATITUDE
        val lon: String = location?.longitude?.toString() ?: BuildConfig.DEFAULT_LOGITUDE

        HungryRepo.getCollections(lat, lon)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<CuratedCollection>>() {
                override fun onNext(t: List<CuratedCollection>?) {
                    Timber.d(t?.get(0)?.toString() ?: "empty")
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {
                    startActivity(Intent(this@LocationAccessPermission,
                            DashboardActivity::class.java))
                }
            })
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //
    }

    override fun onProviderEnabled(provider: String?) {
        //
    }

    override fun onProviderDisabled(provider: String?) {
        //
    }
}