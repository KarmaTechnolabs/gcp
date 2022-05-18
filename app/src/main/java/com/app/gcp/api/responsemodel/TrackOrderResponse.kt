package com.app.gcp.api.responsemodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrackOrderResponse(
    @SerializedName("history")
    val history: List<History>?,
    @SerializedName("order_tracking_number")
    val orderTrackingNumber: String?
) : Parcelable {
    @Parcelize
    data class History(
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
        val statusChangedAt: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}


