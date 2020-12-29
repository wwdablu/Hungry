package com.soumya.wwdablu.hungry.network.model.collections

import com.google.gson.annotations.SerializedName

data class CuratedCollection(
        @SerializedName("collection")
        val collection: CollectionInfo
)
