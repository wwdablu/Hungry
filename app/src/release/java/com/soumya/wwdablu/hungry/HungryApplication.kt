package com.soumya.wwdablu.hungry

import android.app.Application

class HungryApplication : Application() {
    @Override
    fun onCreate() {
        super.onCreate()

        //Init the CacheDB
        CacheDB.init(this)

        //Init analytics
        Analytics.init(this)

        //Realm init
        Realm.init(this)

        //Timber init with release tree
        Timber.plant(HungryReleaseTree())
    }
}