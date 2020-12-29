package com.soumya.wwdablu.hungry.network.model.cuisine

import com.google.gson.annotations.SerializedName

data class CuisineModel(
        @SerializedName("cuisines")
        val list: List<CuisineObject>
)