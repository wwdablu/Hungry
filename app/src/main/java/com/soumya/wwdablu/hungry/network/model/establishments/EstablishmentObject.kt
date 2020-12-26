package com.soumya.wwdablu.hungry.network.model.establishments

import com.google.gson.annotations.SerializedName

data class EstablishmentObject(
        @SerializedName("establishment")
        val establishment: Establishment
)
