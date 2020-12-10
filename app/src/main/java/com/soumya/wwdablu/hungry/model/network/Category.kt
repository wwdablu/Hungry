package com.soumya.wwdablu.hungry.model.network

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("id") val id: Int,
                    @SerializedName("name") val name: String)
