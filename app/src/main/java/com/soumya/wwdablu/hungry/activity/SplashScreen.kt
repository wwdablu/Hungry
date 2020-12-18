package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.model.network.categories.Categories
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SplashScreen : RepoCacheActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        HungryRepo.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<Categories>>() {
                override fun onNext(t: List<Categories>?) {
                    //
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    Thread{
                        proceed()
                    }.start()
                }
            })
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
                    startActivity(Intent(this@SplashScreen, DashboardActivity::class.java))
                    finish()
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