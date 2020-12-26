package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BgColor(
        val type: String,
        val tint: String

) : Parcelable