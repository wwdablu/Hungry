package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
        @SerializedName("restaurant")
        val restaurant: RestaurantInfo
) : Parcelable
