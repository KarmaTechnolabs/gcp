package com.app.gcp.api.requestmodel

import com.google.gson.annotations.SerializedName

data class OrderListRequestModel(
    @field:SerializedName("user_type")
    val user_type: String,

    @field:SerializedName("token")
    val token: String
)
