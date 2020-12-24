package com.soumya.wwdablu.hungry.model.network.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
        var address: String,
        var locality: String,
        var city: String,

        @SerializedName("city_id")
        var cityId: Int,

        var latitude: String,
        var longitude: String,
        var zipcode: String,

        @SerializedName("country_id")
        var countryId: Int,

        @SerializedName("locality_verbose")
        var localityVerbose: String

) : Parcelable