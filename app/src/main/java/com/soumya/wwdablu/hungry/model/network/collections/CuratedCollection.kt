package com.soumya.wwdablu.hungry.model.network.collections

import com.google.gson.annotations.SerializedName

data class CuratedCollection(
        @SerializedName("collection")
        val collection: CollectionInfo
)
