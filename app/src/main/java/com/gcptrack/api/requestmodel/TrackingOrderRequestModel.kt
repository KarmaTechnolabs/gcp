package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class TrackingOrderRequestModel(
    @field:SerializedName("tracking_number")
    val tracking_number: String?
)
