package com.app.gcp.api.requestmodel

import com.google.gson.annotations.SerializedName

data class OrderDetailsRequestModel(
    @field:SerializedName("order_id")
    val orderId: String?,
    @field:SerializedName("token")
    val token: String?
)
