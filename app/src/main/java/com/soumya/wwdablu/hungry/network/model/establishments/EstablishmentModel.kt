package com.soumya.wwdablu.hungry.network.model.establishments

import com.google.gson.annotations.SerializedName

data class EstablishmentModel(

        @SerializedName("establishments")
        val list: List<EstablishmentObject>
)
