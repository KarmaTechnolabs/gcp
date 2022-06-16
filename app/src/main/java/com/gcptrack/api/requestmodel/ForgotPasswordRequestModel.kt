package com.gcptrack.api.requestmodel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ForgotPasswordRequestModel(
    @field:SerializedName("email")
    val email: String
)
