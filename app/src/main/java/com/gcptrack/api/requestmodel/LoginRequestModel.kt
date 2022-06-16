package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class LoginRequestModel(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
