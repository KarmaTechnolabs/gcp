package com.gcptrack.model


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequestModel(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("how_did_you_hear_about_the_app")
    val howDidYouHearAboutTheApp: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("member_of_gcp")
    val memberOfGcp: String? = null,
    @SerializedName("middle_name")
    val middleName: String? = null,
    @SerializedName("occupation")
    val occupation: String? = null,
    @SerializedName("pin_code")
    val pinCode: String? = null,
    @SerializedName("qualification")
    val qualification: String? = null,
    @SerializedName("user_mobile")
    val userMobile: String? = null,
    @SerializedName("village")
    val village: String? = null,
    @SerializedName("state_id")
    var stateId: Int? = null,
    @SerializedName("city_id")
    var cityId: Int? = null
){
    var profilePhoto:String? = null
}
