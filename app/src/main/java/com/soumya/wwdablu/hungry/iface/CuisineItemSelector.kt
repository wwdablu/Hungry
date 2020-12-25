package com.soumya.wwdablu.hungry.iface

import com.soumya.wwdablu.hungry.model.network.cuisine.Cuisine

interface CuisineItemSelector {
    fun onCuisineClicked(cuisine: Cuisine)
}