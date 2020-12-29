package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.network.model.cities.City
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashScreen : HungryActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Thread{
            proceed()
        }.start()
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
                        startActivity(Intent(this@SplashScreen,
                                DashboardActivity::class.java))
                        finish()
                    }
                }
            })
    }

    private fun proceed() {
        val user: UserInfo? = HungryDatabase.getDB(this@SplashScreen)
                .UserInfoDao().getLoggedUser()

        runOnUiThread {
            if(user == null) {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                finish()
            } else {
                fetchCurrentCoordinates()
            }
        }
    }
}