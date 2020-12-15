package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.model.network.Categories
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashScreen : AppCompatActivity() {
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
                    startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                }
            })
    }
}