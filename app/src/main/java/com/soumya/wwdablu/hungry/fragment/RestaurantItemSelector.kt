package com.soumya.wwdablu.hungry.fragment

import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo

interface RestaurantItemSelector {
    fun onRestaurantClicked(restaurant: RestaurantInfo)
}