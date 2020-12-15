package com.soumya.wwdablu.hungry

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class HungryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Init debug version of Timber tree
        Timber.plant(HungryDebugTree())
    }
}