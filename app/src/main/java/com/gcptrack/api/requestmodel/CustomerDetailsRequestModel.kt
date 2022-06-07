package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

data class CustomerDetailsRequestModel(
    @field:SerializedName("user_type")
    val user_type: String,
    @field:SerializedName("client_id")
    val clientId: String?,
    @field:SerializedName("token")
    val token: String?
)
