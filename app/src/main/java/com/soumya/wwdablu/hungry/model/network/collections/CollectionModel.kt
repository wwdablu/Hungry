package com.soumya.wwdablu.hungry.model.network.collections

import com.google.gson.annotations.SerializedName

data class CollectionModel(
        @SerializedName("collections")
        val list: List<CuratedCollection>
)
