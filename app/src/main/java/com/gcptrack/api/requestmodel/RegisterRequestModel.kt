package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class RegisterRequestModel(
    @field:SerializedName("first_name")
    val firstName: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
