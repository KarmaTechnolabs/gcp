package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

data class TrackingOrderRequestModel(
    @field:SerializedName("tracking_number")
    val tracking_number: String?
)
