package com.soumya.wwdablu.hungry.network.model.reviews

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ReviewModel(

        @SerializedName("reviews_count")
        var reviewsCount: Int,

        @SerializedName("user_reviews")
        var reviewList: @RawValue List<UserReview>
) : Parcelable