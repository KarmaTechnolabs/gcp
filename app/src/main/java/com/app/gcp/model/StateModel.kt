package com.app.gcp.model


import com.google.gson.annotations.SerializedName

data class StateModel(
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("state_id")
    val stateId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)