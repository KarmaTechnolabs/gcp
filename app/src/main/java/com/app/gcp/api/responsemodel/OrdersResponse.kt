package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersResponse(
    @SerializedName("order_number")
    val orderNumber: String,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("name")
    val name: String
) : Parcelable