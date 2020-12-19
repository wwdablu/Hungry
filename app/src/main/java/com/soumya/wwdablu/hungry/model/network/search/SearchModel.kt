package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class SearchModel(
        @SerializedName("results_found")
        val resultsFound: Int,

        @SerializedName("results_start")
        val resultsStart: Int,

        @SerializedName("results_shown")
        val resultsShown: Int,

        @SerializedName("restaurants")
        val restaurants: List<Restaurant>
)
