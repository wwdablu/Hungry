package com.soumya.wwdablu.hungry.network.model.cities

import com.google.gson.annotations.SerializedName

data class CityModel(

        @SerializedName("location_suggestions")
        val model: List<City>,

        @SerializedName("status")
        val status: String
)
