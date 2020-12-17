package com.soumya.wwdablu.hungry.model.network.establishments

import com.google.gson.annotations.SerializedName

data class EstablishmentObject(
        @SerializedName("establishment")
        val establishment: Establishment
)
