package com.soumya.wwdablu.hungry.common

import androidx.annotation.DrawableRes
import com.soumya.wwdablu.hungry.R
import java.util.*

object DefaultCuisineImage {
    private var cuisineDefaultCardImage: HashMap<String?, Int?> = HashMap()

    init {
        initCuisineDefaultCardImage()
    }

    @DrawableRes
    @Synchronized
    fun getCuisineImage(cuisine: String?): Int {
        return if (cuisineDefaultCardImage.containsKey(cuisine?.toLowerCase())) {
            cuisineDefaultCardImage[cuisine?.toLowerCase()]!!
        } else {
            cuisineDefaultCardImage["default"]!!
        }
    }

    private fun initCuisineDefaultCardImage() {

        //No need to insert, already its done
        if (0 != cuisineDefaultCardImage.size) {
            return
        }

        cuisineDefaultCardImage["indian"] = R.drawable.default_food_indian
        cuisineDefaultCardImage["mexican"] = R.drawable.default_food_mexican
        cuisineDefaultCardImage["american"] = R.drawable.default_food_american
        cuisineDefaultCardImage["chinese"] = R.drawable.default_food_chinese
        cuisineDefaultCardImage["italian"] = R.drawable.default_food_italian
        cuisineDefaultCardImage["japanese"] = R.drawable.default_food_japanese
        cuisineDefaultCardImage["default"] = R.drawable.default_food
    }

    init {
        initCuisineDefaultCardImage()
    }
}