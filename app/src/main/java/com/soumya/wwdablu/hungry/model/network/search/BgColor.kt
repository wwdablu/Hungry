package com.soumya.wwdablu.hungry.model.network.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BgColor(
        val type: String,
        val tint: String

) : Parcelable