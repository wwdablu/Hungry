package com.soumya.wwdablu.zomatobuddy;

import android.app.Application;

import com.soumya.wwdablu.zomatobuddy.common.Analytics;
import com.soumya.wwdablu.zomatobuddy.database.CacheDB;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class ZomatoBuddyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        //Init the CacheDB
        CacheDB.init(this);

        //Init analytics
        Analytics.init(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + "[" + element.getLineNumber() + "]";
                }
            });
        }
    }
}
