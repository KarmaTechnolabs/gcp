package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

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
