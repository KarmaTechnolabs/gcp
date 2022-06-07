package com.gcptrack.api.requestmodel

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestModel(

	@field:SerializedName("user_type")
	val user_type: String?,

	@field:SerializedName("token")
	val token: String?,

	@field:SerializedName("password")
	val password: String?
)
