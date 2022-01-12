package com.app.gcp.model


import com.google.gson.annotations.SerializedName

data class StateRequestModel(
    @SerializedName("country_id")
    val countryId: String = "101" // static for india
)