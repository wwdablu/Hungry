package com.soumya.wwdablu.hungry.viewmodel

import androidx.annotation.Nullable
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo

class RestaurantDetailsViewModel() : HungryViewModel() {

    private lateinit var mRestautantInfo: RestaurantInfo

    fun setRestaurantInfo(restaurantInfo: RestaurantInfo) {
        mRestautantInfo = restaurantInfo
    }

    @Nullable
    fun getRestaurantInfo() : RestaurantInfo? {
        return if(this::mRestautantInfo.isInitialized)
            mRestautantInfo
        else
            null
    }
}