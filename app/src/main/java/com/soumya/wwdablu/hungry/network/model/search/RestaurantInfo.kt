package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class RestaurantInfo(

        @SerializedName("R")
        var r: R,
        var apikey: String,
        var id: String,
        var name: String,
        var url: String,
        var location: Location,

        @SerializedName("switch_to_order_menu")
        var switchToOrderMenu: Int,

        var cuisines: String,
        var timings: String,

        @SerializedName("average_cost_for_two")
        var averageCostForTwo: Int,

        @SerializedName("price_range")
        var priceRange: Int,

        var currency: String,
        var highlights: List<String>,

        @SerializedName("is_zomato_book_res")
        var isZomatoBookRes: Int,

        @SerializedName("is_book_form_web_view")
        var isBookFormWebView: Int,

        @SerializedName("book_form_web_view_url")
        var bookFormWebViewUrl: String,

        @SerializedName("book_again_url")
        var bookAgainUrl: String,

        var thumb: String,

        @SerializedName("user_rating")
        var userRating: UserRating,

        @SerializedName("all_reviews_count")
        var allReviewsCount: Int,

        @SerializedName("photos_url")
        var photosUrl: String,

        @SerializedName("photo_count")
        var photoCount: Int,

        @SerializedName("menu_url")
        var menuUrl: String,

        @SerializedName("featured_image")
        var featuredImage: String,

        @SerializedName("has_online_delivery")
        var hasOnlineDelivery: Int,

        @SerializedName("is_delivering_now")
        var isDeliveringNow: Int,

        @SerializedName("phone_numbers")
        var phoneNumbers: String,

        var establishment: List<String>

) : Parcelable {
    companion object {
        fun calculateDistance(fromLat: String, fromLon: String,
                toLat: String, toLon: String) : String {

            val from: android.location.Location = android.location.Location("from")
            val to: android.location.Location = android.location.Location("from")

            from.latitude = fromLat.toDouble()
            from.longitude = fromLon.toDouble()

            to.latitude = toLat.toDouble()
            to.longitude = toLon.toDouble()

            val distance: Float = from.distanceTo(to)
            return "${(distance/1000).roundToInt()}"
        }
    }
}
