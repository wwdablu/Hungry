package com.soumya.wwdablu.hungry.model.network.cities

import com.google.gson.annotations.SerializedName

data class CityModel(

        @SerializedName("location_suggestions")
        val model: List<City>,

        @SerializedName("status")
        val status: String
)
