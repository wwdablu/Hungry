package com.soumya.wwdablu.zomatobuddy;

import android.app.Application;

import com.soumya.wwdablu.zomatobuddy.common.Analytics;
import com.soumya.wwdablu.zomatobuddy.database.CacheDB;

import io.realm.Realm;
import timber.log.Timber;

public class ZomatoBuddyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Init the CacheDB
        CacheDB.init(this);

        //Init analytics
        Analytics.init(this);

        //Realm init
        Realm.init(this);

        //Timber init with release tree
        Timber.plant(new BuddyReleaseTree());
    }
}
