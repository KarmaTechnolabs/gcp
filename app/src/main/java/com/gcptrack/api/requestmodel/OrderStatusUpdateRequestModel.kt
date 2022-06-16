package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class OrderStatusUpdateRequestModel(
    @field:SerializedName("order_id")
    val orderId: String?,
    @field:SerializedName("status_id")
    val statusId: String?,
    @field:SerializedName("note")
    val note: String?,

    @field:SerializedName("token")
    val token: String
)
