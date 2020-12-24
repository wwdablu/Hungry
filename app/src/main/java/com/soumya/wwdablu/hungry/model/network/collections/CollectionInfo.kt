package com.soumya.wwdablu.hungry.model.network.collections

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionInfo(
        @SerializedName("collection_id")
        val id: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("image_url")
        val imageUrl: String,

        @SerializedName("res_count")
        val count: String,

        @SerializedName("share_url")
        val shareUrl: String
) : Parcelable
