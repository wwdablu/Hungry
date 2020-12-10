package com.soumya.wwdablu.zomatobuddy

import android.app.Application
import timber.log.Timber

class ZomatoBuddyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Init debug version of Timber tree
        Timber.plant(BuddyDebugTree())
    }
}