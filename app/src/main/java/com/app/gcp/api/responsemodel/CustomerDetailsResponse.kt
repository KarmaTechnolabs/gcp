package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class CustomerDetailsResponse(
    @SerializedName("client")
    val client: Client?,
    @SerializedName("orders")
    val orders: List<Order>?

) : Parcelable {
    @Parcelize
    data class Client(
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
    ) : Parcelable

    @Parcelize
    data class Order(
        @SerializedName("client_id")
        val clientId: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("created_by")
        val createdBy: String?,
        @SerializedName("deleted")
        val deleted: String?,
        @SerializedName("discount_amount")
        val discountAmount: String?,
        @SerializedName("discount_amount_type")
        val discountAmountType: String?,
        @SerializedName("discount_type")
        val discountType: String?,
        @SerializedName("excess_quantity")
        val excessQuantity: String?,
        @SerializedName("files")
        val files: String?,
        @SerializedName("gst_amount")
        val gstAmount: String?,
        @SerializedName("gst_percentage")
        val gstPercentage: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("note")
        val note: String?,
        @SerializedName("order_date")
        val orderDate: String?,
        @SerializedName("project_id")
        val projectId: String?,
        @SerializedName("shipping_address")
        val shippingAddress: String?,
        @SerializedName("stage_id")
        val stageId: String?,
        @SerializedName("status_id")
        val statusId: String?,
        @SerializedName("tax_id")
        val taxId: String?,
        @SerializedName("tax_id2")
        val taxId2: String?,
        @SerializedName("tracking_number")
        val trackingNumber: String?
    ) : Parcelable
}