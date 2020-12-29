package com.soumya.wwdablu.hungry.iface

import com.soumya.wwdablu.hungry.network.model.cuisine.Cuisine

interface CuisineItemSelector {
    fun onCuisineClicked(cuisine: Cuisine)
}