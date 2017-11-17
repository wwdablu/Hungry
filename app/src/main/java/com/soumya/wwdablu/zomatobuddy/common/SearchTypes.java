package com.soumya.wwdablu.zomatobuddy.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SearchTypes {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SEARCH_DINE_OUT,
            SEARCH_DELIVERY,
            SEARCH_TAKE_AWAY
    })
    public @interface SearchType{}

    public static final String SEARCH_DINE_OUT = "2";
    public static final String SEARCH_DELIVERY = "1";
    public static final String SEARCH_TAKE_AWAY = "5";
}
