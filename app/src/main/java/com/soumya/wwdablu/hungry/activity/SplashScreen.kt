package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.model.network.CategoriesModel
import com.soumya.wwdablu.hungry.network.DataProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        
        DataProvider.call().getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CategoriesModel>() {
                    override fun onNext(t: CategoriesModel?) {
                        if (t != null) {
                            Timber.d(t.categories[0].category.name)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Timber.e(e)
                    }

                    override fun onComplete() {
                        Timber.d("Done")
                    }

                })
    }
}