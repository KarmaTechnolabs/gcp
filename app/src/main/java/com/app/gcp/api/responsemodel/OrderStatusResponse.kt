package com.app.gcp.api.responsemodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class OrderStatusResponse(
    @SerializedName("color")
    val color: String?,
    @SerializedName("deleted")
    val deleted: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("sort")
    val sort: String?,
    @SerializedName("title")
    val title: String?
) : Parcelable