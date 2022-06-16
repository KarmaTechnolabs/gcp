package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class OrderDetailsRequestModel(
    @field:SerializedName("user_type")
    val user_type: String,
    @field:SerializedName("order_id")
    val orderId: String?,
    @field:SerializedName("token")
    val token: String?
)
