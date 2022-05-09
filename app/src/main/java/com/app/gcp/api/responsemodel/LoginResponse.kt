package com.app.gcp.api.responsemodel

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("first_name")
    val firstName: String,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("role_id")
    val role_id: String,

    @field:SerializedName("is_admin")
    val is_admin: String,

    @field:SerializedName("auth_token")
    val auth_token: String
){

    fun getFullName(): String {
        var name = firstName
        if (!lastName.isNullOrBlank()) {
            name = "$name $lastName"
        }
        return name
    }
}