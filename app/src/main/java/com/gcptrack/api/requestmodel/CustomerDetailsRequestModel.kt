package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CustomerDetailsRequestModel(
    @field:SerializedName("user_type")
    val user_type: String,
    @field:SerializedName("client_id")
    val clientId: String?,
    @field:SerializedName("token")
    val token: String?
)
