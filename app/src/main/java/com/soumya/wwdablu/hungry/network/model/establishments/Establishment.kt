package com.soumya.wwdablu.hungry.network.model.establishments

import com.google.gson.annotations.SerializedName

data class Establishment(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String
)
