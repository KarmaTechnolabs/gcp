package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class OrdersDetailsResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("client_id")
    val clientId: String?,
    @SerializedName("company_name")
    val companyName: String?,
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
    @SerializedName("order_status")
    val orderStatus: String?,
    @SerializedName("project_id")
    val projectId: String?,
    @SerializedName("shipping_address")
    val shippingAddress: String?,
    @SerializedName("stage_id")
    val stageId: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("status_id")
    val statusId: String?,
    @SerializedName("tax_id")
    val taxId: String?,
    @SerializedName("tax_id2")
    val taxId2: String?,
    @SerializedName("tracking_number")
    val trackingNumber: String?,
    @SerializedName("zip")
    val zip: String?
) : Parcelable {
    @Parcelize
    data class Product(
        @SerializedName("created_by")
        val createdBy: String?,
        @SerializedName("deleted")
        val deleted: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("excess_quantity")
        val excessQuantity: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("item_id")
        val itemId: String?,
        @SerializedName("order_id")
        val orderId: String?,
        @SerializedName("quantity")
        val quantity: String?,
        @SerializedName("rate")
        val rate: String?,
        @SerializedName("sort")
        val sort: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("total")
        val total: String?,
        @SerializedName("unit_type")
        val unitType: String?
    ) : Parcelable
}