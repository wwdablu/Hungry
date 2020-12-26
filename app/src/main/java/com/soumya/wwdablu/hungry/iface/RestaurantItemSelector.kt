package com.soumya.wwdablu.hungry.iface

import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo

interface RestaurantItemSelector {
    fun onRestaurantClicked(restaurant: RestaurantInfo)
}