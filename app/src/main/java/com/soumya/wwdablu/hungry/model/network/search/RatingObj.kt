package com.soumya.wwdablu.hungry.model.network.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingObj (
    var title: Title,

    @SerializedName("bg_color")
    var bgColor: BgColor

) : Parcelable