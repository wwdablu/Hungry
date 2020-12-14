package com.soumya.wwdablu.hungry.common

import androidx.annotation.StringDef

object SearchTypes {
    const val SEARCH_DINE_OUT: String = "2"
    const val SEARCH_DELIVERY: String = "1"
    const val SEARCH_TAKE_AWAY: String = "5"
    const val SEARCH_FAVOURITE: String = "999"

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @StringDef(SEARCH_DINE_OUT, SEARCH_DELIVERY, SEARCH_TAKE_AWAY, SEARCH_FAVOURITE)
    annotation class SearchType
}