package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingObj (
    var title: Title,

    @SerializedName("bg_color")
    var bgColor: BgColor

) : Parcelable