package com.soumya.wwdablu.hungry.model.network.establishments

import com.google.gson.annotations.SerializedName

data class Establishment(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String
)
