package com.soumya.wwdablu.hungry.network.model.reviews

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review (

    val rating: Float,

    @SerializedName("review_text")
    val reviewText: String,

    val id: Int,

    @SerializedName("rating_color")
    val ratingColor: String,

    @SerializedName("review_time_friendly")
    val reviewTimeFriendly: String,

    @SerializedName("rating_text")
    val ratingText: String,

    val timestamp: Int,
    val likes: Int,
    val user: User,

    @SerializedName("comments_count")
    val commentsCount: Int

) : Parcelable