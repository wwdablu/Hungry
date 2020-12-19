package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class HasMenuStatus(
        @SerializedName("delivery")
        val delivery: Any,

        @SerializedName("takeaway")
        val takeAway: Int
)
