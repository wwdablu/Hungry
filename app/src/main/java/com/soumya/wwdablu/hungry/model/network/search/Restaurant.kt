package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class Restaurant(
        @SerializedName("restaurant")
        val restaurant: RestaurantInfo
)
