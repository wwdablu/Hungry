package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.databinding.ActivityLocationAccessPermissionBinding
import com.soumya.wwdablu.hungry.model.network.cities.City
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class LocationAccess : HungryActivity() {

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

        HungryRepo.getCity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<City>>() {
                override fun onNext(t: List<City>?) {
                    //
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    runOnUiThread {
                        runOnUiThread {
                            startActivity(Intent(this@LocationAccess,
                                    DashboardActivity::class.java))
                            finish()
                        }
                    }
                }
            })
    }
}