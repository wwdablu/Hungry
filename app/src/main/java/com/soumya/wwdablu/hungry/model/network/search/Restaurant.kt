package com.soumya.wwdablu.hungry.model.network.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
        @SerializedName("restaurant")
        val restaurant: RestaurantInfo
) : Parcelable
