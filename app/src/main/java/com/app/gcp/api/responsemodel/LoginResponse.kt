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

    @field:SerializedName("user_type")
    val user_type: String?,

    @field:SerializedName("role_id")
    val role_id: String,

    @field:SerializedName("is_admin")
    val is_admin: String,

    @field:SerializedName("auth_token")
    val auth_token: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("client_migration_date")
    val clientMigrationDate: String,
    @SerializedName("company_contact_details")
    val companyContactDetails: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("currency_symbol")
    val currencySymbol: String,
    @SerializedName("deleted")
    val deleted: String,
    @SerializedName("disable_online_payment")
    val disableOnlinePayment: String,
    @SerializedName("group_ids")
    val groupIds: String,
    @SerializedName("gst_number")
    val gstNumber: String,
    @SerializedName("is_lead")
    val isLead: String,
    @SerializedName("last_lead_status")
    val lastLeadStatus: String,
    @SerializedName("lead_source_id")
    val leadSourceId: String,
    @SerializedName("lead_status_id")
    val leadStatusId: String,
    @SerializedName("owner_id")
    val ownerId: String,
    @SerializedName("sort")
    val sort: String,
    @SerializedName("starred_by")
    val starredBy: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("vat_number")
    val vatNumber: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("zip")
    val zip: String,
    @SerializedName("is_password_change")
    var isPasswordChange: String,

)

