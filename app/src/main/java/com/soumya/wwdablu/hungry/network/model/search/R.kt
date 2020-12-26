package com.soumya.wwdablu.hungry.network.model.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class R(
        @SerializedName("res_id")
        val resId: Int,

        @SerializedName("is_grocery_store")
        val isGroceryStore: Boolean,

        @SerializedName("has_menu_status")
        val hasMenuStatus: HasMenuStatus

) : Parcelable
