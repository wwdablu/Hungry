package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.databinding.ActivityLocationAccessPermissionBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LocationAccess : RepoCacheActivity() {

    private lateinit var mViewBinding: ActivityLocationAccessPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLocationAccessPermissionBinding.inflate(layoutInflater)

        mViewBinding.btnAutoLocation.setOnClickListener {
            fetchCurrentCoordinates()
        }

        setContentView(mViewBinding.root)
    }

    override fun onLocationUpdated(location: Location?) {

        cacheInformation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<Boolean>() {
                override fun onNext(t: Boolean?) {
                    //
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                    finish()
                }

                override fun onComplete() {
                    startActivity(Intent(this@LocationAccess,
                            DashboardActivity::class.java))
                    finish()
                }
            })
    }
}