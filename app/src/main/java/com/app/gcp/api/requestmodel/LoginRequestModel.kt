package com.app.gcp.api.requestmodel

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
