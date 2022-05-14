package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class OrdersResponse(
    @SerializedName("client_id")
    val clientId: String?="",
    @SerializedName("created_at")
    val createdAt: String?="",
    @SerializedName("created_by")
    val createdBy: String?="",
    @SerializedName("deleted")
    val deleted: String?="",
    @SerializedName("discount_amount")
    val discountAmount: String?="",
    @SerializedName("discount_amount_type")
    val discountAmountType: String?="",
    @SerializedName("discount_type")
    val discountType: String?="",
    @SerializedName("excess_quantity")
    val excessQuantity: String?="",
    @SerializedName("files")
    val files: String?="",
    @SerializedName("gst_amount")
    val gstAmount: String?="",
    @SerializedName("gst_percentage")
    val gstPercentage: String?="",
    @SerializedName("id")
    val id: String?="",
    @SerializedName("note")
    val note: String?="",
    @SerializedName("order_date")
    val orderDate: String?="",
    @SerializedName("order_status")
    val orderStatus: String?="",
    @SerializedName("project_id")
    val projectId: String?="",
    @SerializedName("shipping_address")
    val shippingAddress: String?="",
    @SerializedName("stage_id")
    val stageId: String?="",
    @SerializedName("status_id")
    val statusId: String?="",
    @SerializedName("tax_id")
    val taxId: String?="",
    @SerializedName("tax_id2")
    val taxId2: String?="",
    @SerializedName("tracking_number")
    val trackingNumber: String?="",
    @SerializedName("current_stage_id")
    val currentStage_Id: String?="",
    @SerializedName("company_name")
    val companyName: String?=""


) : Parcelable