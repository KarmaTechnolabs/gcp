package com.gcptrack.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class OrdersDetailsResponse(
    @SerializedName("details")
    val details: Details?,
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("customFields")
    val customFields: List<CustomField>?,
    @SerializedName("pdf")
    val pdf: String?,
    @SerializedName("orderStagesHistory")
    val orderStagesHistory: List<OrderStagesHistory>?

    ) : Parcelable {
    @Parcelize
    public data class Details(
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
        val zip: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("phone")
        val phone: String?
    ) : Parcelable

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

    @Parcelize
    data class CustomField(
        @SerializedName("add_filter")
        val addFilter: String?,
        @SerializedName("custom_field_id")
        val customFieldId: String?,
        @SerializedName("deleted")
        val deleted: String?,
        @SerializedName("disable_editing_by_clients")
        val disableEditingByClients: String?,
        @SerializedName("example_variable_name")
        val exampleVariableName: String?,
        @SerializedName("field_type")
        val fieldType: String?,
        @SerializedName("hide_from_clients")
        val hideFromClients: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("options")
        val options: String?,
        @SerializedName("placeholder")
        val placeholder: String?,
        @SerializedName("related_to")
        val relatedTo: String?,
        @SerializedName("related_to_id")
        val relatedToId: String?,
        @SerializedName("related_to_type")
        val relatedToType: String?,
        @SerializedName("required")
        val required: String?,
        @SerializedName("show_in_contract")
        val showInContract: String?,
        @SerializedName("show_in_estimate")
        val showInEstimate: String?,
        @SerializedName("show_in_invoice")
        val showInInvoice: String?,
        @SerializedName("show_in_order")
        val showInOrder: String?,
        @SerializedName("show_in_proposal")
        val showInProposal: String?,
        @SerializedName("show_in_table")
        val showInTable: String?,
        @SerializedName("show_on_kanban_card")
        val showOnKanbanCard: String?,
        @SerializedName("sort")
        val sort: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("value")
        val value: String?,
        @SerializedName("visible_to_admins_only")
        val visibleToAdminsOnly: String?
    ) : Parcelable

    @Parcelize
    data class OrderStagesHistory(
        @SerializedName("color")
        val color: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("deleted")
        val deleted: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("is_current")
        val isCurrent: String?,
        @SerializedName("notes")
        val notes: String?,
        @SerializedName("order_id")
        val orderId: String?,
        @SerializedName("sort")
        val sort: String?,
        @SerializedName("stage_id")
        val stageId: String?,
        @SerializedName("status_changed_at")
        val statusChangedAt: String?="",
        @SerializedName("title")
        val title: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}



