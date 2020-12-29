package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class HasMenuStatus(

        @SerializedName("delivery")
        val delivery: @RawValue Any,

        @SerializedName("takeaway")
        val takeAway: Int

) : Parcelable
