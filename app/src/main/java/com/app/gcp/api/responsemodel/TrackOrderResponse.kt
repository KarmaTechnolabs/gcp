package com.app.gcp.api.responsemodel

import com.google.gson.annotations.SerializedName

data class TrackOrderResponse(
    @SerializedName("history")
    val history: List<Any>?,
    @SerializedName("order_details")
    val orderDetails: OrderDetails?,
    @SerializedName("order_stages")
    val orderStages: OrderStages?,
    @SerializedName("order_stages_history")
    val orderStagesHistory: List<Any>?,
    @SerializedName("order_tracking_number")
    val orderTrackingNumber: String?
)

data class OrderDetails(
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
)

data class OrderStages(
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
)