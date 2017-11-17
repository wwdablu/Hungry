package com.soumya.wwdablu.zomatobuddy;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import javax.inject.Inject;

import timber.log.Timber;

public class ZomatoBuddyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(this).build();

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
