package com.soumya.wwdablu.hungry.common

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object SearchTypes {
    const val SEARCH_DINE_OUT: String = "2"
    const val SEARCH_DELIVERY: String = "1"
    const val SEARCH_TAKE_AWAY: String = "5"
    const val SEARCH_FAVOURITE: String = "999"

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(SEARCH_DINE_OUT, SEARCH_DELIVERY, SEARCH_TAKE_AWAY, SEARCH_FAVOURITE)
    annotation class SearchType
}