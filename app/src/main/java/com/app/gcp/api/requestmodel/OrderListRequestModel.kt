package com.app.gcp.api.requestmodel

import com.google.gson.annotations.SerializedName

data class OrderListRequestModel(
    @field:SerializedName("status_id")
    val status_id: String,

    @field:SerializedName("client_id")
    val client_id: String,

    @field:SerializedName("token")
    val token: String
)
