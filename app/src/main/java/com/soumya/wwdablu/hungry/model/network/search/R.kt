package com.soumya.wwdablu.hungry.model.network.search

import com.google.gson.annotations.SerializedName

data class R(
        @SerializedName("res_id")
        val resId: Int,

        @SerializedName("is_grocery_store")
        val isGroceryStore: Boolean,

        @SerializedName("has_menu_status")
        val hasMenuStatus: HasMenuStatus
)
