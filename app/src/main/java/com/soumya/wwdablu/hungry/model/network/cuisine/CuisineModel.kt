package com.soumya.wwdablu.hungry.model.network.cuisine

import com.google.gson.annotations.SerializedName

data class CuisineModel(
        @SerializedName("cuisines")
        val list: List<CuisineObject>
)