package com.soumya.wwdablu.hungry.model.network.cities

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("country_id")
        val countryId: String,

        @SerializedName("country_name")
        val countryName: String,

        @SerializedName("is_state")
        val isState: Int,

        @SerializedName("state_id")
        val stateId: String,

        @SerializedName("state_name")
        val stateName: String,

        @SerializedName("state_code")
        val stateCode: String,

        @SerializedName("country_flag_url")
        val countryFlagUrl: String
)
