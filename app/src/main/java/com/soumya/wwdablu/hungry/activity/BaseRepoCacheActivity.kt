package com.soumya.wwdablu.hungry.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.soumya.wwdablu.hungry.BuildConfig
import com.soumya.wwdablu.hungry.model.network.cities.City
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

abstract class BaseRepoCacheActivity : AppCompatActivity(), LocationListener {

    private var mLat: String = BuildConfig.DEFAULT_LATITUDE
    private var mLon: String = BuildConfig.DEFAULT_LOGITUDE
    private lateinit var mCity: City

    internal abstract fun onLocationUpdated(location: Location?)

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

    override fun onLocationChanged(location: Location?) {
        val locationManager: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(this)

        mLat = location?.latitude?.toString() ?: BuildConfig.DEFAULT_LATITUDE
        mLon = location?.longitude?.toString() ?: BuildConfig.DEFAULT_LOGITUDE

        HungryRepo.setLocation(mLat, mLon)

        onLocationUpdated(location)
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

    internal fun fetchCurrentCoordinates() {

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

    fun cacheInformation() : Observable<Boolean> {

        return Observable.create { it ->

            HungryRepo.getCity()
                .flatMap { me ->

                    mCity = me[0]
                    if(me.isEmpty()) {
                        HungryRepo.getCollections()
                    } else {
                        HungryRepo.getCollections()
                    }

                    HungryRepo.getCuisine()
                }
                .flatMap {
                    HungryRepo.getCategories()
                }
                .flatMap {
                    HungryRepo.getEstablishments()
                }
                .flatMap {
                    HungryRepo.getCollections()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: DisposableObserver<List<CuratedCollection>>() {
                    override fun onNext(t: List<CuratedCollection>?) {
                        Timber.d(t?.get(0)?.toString() ?: "empty")
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }
}