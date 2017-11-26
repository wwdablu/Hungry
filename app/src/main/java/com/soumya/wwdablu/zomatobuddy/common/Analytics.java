package com.soumya.wwdablu.zomatobuddy.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Analytics {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            EVENT_APP_START,
            EVENT_DETAILS_ITEM,
            EVENT_RESTAURANT_DETAILS,
            EVENT_SEARCH
    })
    public @interface EventType{}

    public static final String EVENT_SEARCH = FirebaseAnalytics.Event.SEARCH;
    public static final String EVENT_RESTAURANT_DETAILS = FirebaseAnalytics.Event.VIEW_ITEM_LIST;
    public static final String EVENT_DETAILS_ITEM = FirebaseAnalytics.Event.SELECT_CONTENT;
    public static final String EVENT_APP_START = FirebaseAnalytics.Event.APP_OPEN;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            PARAM_APP_START,
            PARAM_DETAIL_ITEM_SELECTED,
            PARAM_RESTAURANT_SELECTED,
            PARAM_SEARCH_TERM
    })
    public @interface ParamType{}

    public static final String PARAM_SEARCH_TERM = FirebaseAnalytics.Param.SEARCH_TERM;
    public static final String PARAM_RESTAURANT_SELECTED = FirebaseAnalytics.Param.ORIGIN;
    public static final String PARAM_DETAIL_ITEM_SELECTED = FirebaseAnalytics.Param.SOURCE;
    public static final String PARAM_APP_START = "ZBuddyUser";

    private static FirebaseAnalytics analytics;

    public static void init(Context context) {

        if(null == analytics) {
            analytics = FirebaseAnalytics.getInstance(context);
            analytics.setAnalyticsCollectionEnabled(true);
            analytics.setMinimumSessionDuration(5000);
            analytics.setSessionTimeoutDuration(30000);
        }
    }

    public static void setAnalyiticsFor(String user) {
        analytics.setUserId(user);
    }

    public static void setCurrentScreen(@NonNull Activity activity, @NonNull String screenName) {
        analytics.setCurrentScreen(activity, screenName, null);
    }

    public static void setUserAction(@NonNull @EventType String event, @NonNull @ParamType String param, String data) {

        Bundle bundle = new Bundle();
        bundle.putString(param, data);
        analytics.logEvent(event, bundle);
    }
}
