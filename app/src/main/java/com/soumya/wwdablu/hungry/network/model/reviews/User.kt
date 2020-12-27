package com.soumya.wwdablu.hungry.network.model.reviews

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name: String,

    @SerializedName("zomato_handle")
    val zomatoHandle: String,

    @SerializedName("foodie_color")
    val foodieColor: String,

    @SerializedName("profile_url")
    val profileUrl: String,

    @SerializedName("profile_image")
    val profileImage: String,

    @SerializedName("profile_deeplink")
    val profileDeeplink: String,

    @SerializedName("foodie_level")
    val foodieLevel: String,

    @SerializedName("foodie_level_num")
    val foodieLevelNum: Int

) : Parcelable
