package com.gcptrack.api.responsemodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CreatedAt(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("timezone_type")
    val timezoneType: Int
)
