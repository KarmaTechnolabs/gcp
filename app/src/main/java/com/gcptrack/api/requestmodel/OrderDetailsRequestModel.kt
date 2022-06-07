package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

data class OrderDetailsRequestModel(
    @field:SerializedName("user_type")
    val user_type: String,
    @field:SerializedName("order_id")
    val orderId: String?,
    @field:SerializedName("token")
    val token: String?
)
