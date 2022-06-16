package com.gcptrack.api.responsemodel


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Keep
@Parcelize
data class OrderStatusResponse(

    @SerializedName("order_status")
    val orderStatus: List<OrderStatus>?,
    @SerializedName("users")
    val users: Users?

) : Parcelable {
    @Parcelize
    data class OrderStatus(
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

    @Parcelize
    data class Users(
        @SerializedName("address")
        val address: String?,
        @SerializedName("alternative_address")
        val alternativeAddress: String?,
        @SerializedName("alternative_phone")
        val alternativePhone: String?,
        @SerializedName("auth_token")
        val authToken: String?,
        @SerializedName("client_id")
        val clientId: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("deleted")
        val deleted: String?,
        @SerializedName("disable_login")
        val disableLogin: String?,
        @SerializedName("dob")
        val dob: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("enable_email_notification")
        val enableEmailNotification: String?,
        @SerializedName("enable_web_notification")
        val enableWebNotification: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("is_admin")
        val isAdmin: String?,
        @SerializedName("is_password_change")
        val isPasswordChange: String?,
        @SerializedName("is_permission_updated")
        var isPermissionUpdated: String?,
        @SerializedName("is_primary_contact")
        val isPrimaryContact: String?,
        @SerializedName("job_title")
        val jobTitle: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("last_online")
        val lastOnline: String?,
        @SerializedName("message_checked_at")
        val messageCheckedAt: String?,
        @SerializedName("note")
        val note: String?,
        @SerializedName("notification_checked_at")
        val notificationCheckedAt: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("requested_account_removal")
        val requestedAccountRemoval: String?,
        @SerializedName("role_id")
        val roleId: String?,
        @SerializedName("skype")
        val skype: String?,
        @SerializedName("ssn")
        val ssn: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("sticky_note")
        val stickyNote: String?,
        @SerializedName("user_type")
        val userType: String?
    ) : Parcelable
}
