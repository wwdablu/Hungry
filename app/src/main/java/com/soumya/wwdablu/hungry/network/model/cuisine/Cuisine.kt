package com.soumya.wwdablu.hungry.network.model.cuisine

import com.google.gson.annotations.SerializedName

data class Cuisine(
        @SerializedName("cuisine_id")
        val cuisineId: String,

        @SerializedName("cuisine_name")
        val cuisineName: String
)