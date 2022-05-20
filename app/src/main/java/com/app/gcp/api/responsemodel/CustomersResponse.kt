package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class CustomersResponse(

    @SerializedName("address")
    val address: String?,

    @SerializedName("auth_token")
    val authToken: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("client_migration_date")
    val clientMigrationDate: String?,

    @SerializedName("company_contact_details")
    val companyContactDetails: String?,

    @SerializedName("company_name")
    val companyName: String?,

    @SerializedName("country")
    val country: String?,

    @SerializedName("created_by")
    val createdBy: String?,

    @SerializedName("created_date")
    val createdDate: String?,

    @SerializedName("currency")
    val currency: String?,

    @SerializedName("currency_symbol")
    val currencySymbol: String?,

    @SerializedName("deleted")
    val deleted: String?,

    @SerializedName("disable_online_payment")
    val disableOnlinePayment: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("group_ids")
    val groupIds: String?,

    @SerializedName("gst_number")
    val gstNumber: String?,

    @SerializedName("id")
    val id: String?,

    @SerializedName("is_lead")
    val isLead: String?,

    @SerializedName("is_password_change")
    val isPasswordChange: String?,

    @SerializedName("last_lead_status")
    val lastLeadStatus: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("lead_source_id")
    val leadSourceId: String?,

    @SerializedName("lead_status_id")
    val leadStatusId: String?,

    @SerializedName("owner_id")
    val ownerId: String?,

    @SerializedName("password")
    val password: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("sort")
    val sort: String?,

    @SerializedName("starred_by")
    val starredBy: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("uuid")
    val uuid: String?,

    @SerializedName("vat_number")
    val vatNumber: String?,

    @SerializedName("website")
    val website: String?,

    @SerializedName("zip")
    val zip: String?
): Parcelable
