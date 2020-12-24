package com.soumya.wwdablu.hungry.fragment.iface

import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo

interface RestaurantItemSelector {
    fun onRestaurantClicked(restaurant: RestaurantInfo)
}