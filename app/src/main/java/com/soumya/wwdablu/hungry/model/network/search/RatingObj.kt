package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class RatingObj (
    var title: Title,

    @SerializedName("bg_color")
    var bgColor: BgColor
)