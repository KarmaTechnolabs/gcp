package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

data class OrderStatusRequestModel(
    @field:SerializedName("tracking_number")
    val tracking_number: String?,

    @field:SerializedName("user_type")
    val user_type: String,

    @field:SerializedName("token")
    val token: String
)
