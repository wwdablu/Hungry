package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class UserRating (

        @SerializedName("aggregate_rating")
        var aggregateRating: String,

        @SerializedName("rating_text")
        var ratingText: String,

        @SerializedName("rating_color")
        var ratingColor: String,

        @SerializedName("rating_obj")
        var ratingObj: RatingObj,

        var votes: Int
)
