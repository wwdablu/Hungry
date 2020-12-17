package com.soumya.wwdablu.hungry.model.network.establishments

import com.google.gson.annotations.SerializedName

data class EstablishmentModel(

        @SerializedName("establishments")
        val list: List<EstablishmentObject>
)
