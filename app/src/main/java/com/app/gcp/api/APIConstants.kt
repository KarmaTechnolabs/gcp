package com.app.gcp.api

object APIConstants {
    const val SUCCESS = 200
    const val FAILURE = 0

    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_BEARER = "Bearer "

    //OnBoarding APIs
    const val API_SIGN_IN = "user_login"
    const val API_ORDERS = "orders"
    const val API_ORDER_STATUS = "order_status"
    const val API_REGISTER = "register"
    const val API_FORGOT_PASSWORD = "send_reset_password_mail"
    const val API_TRACK_ORDER = "track"
    const val API_LOGOUT = "logout"
    const val API_CHANGE_PASSWORD = "changePassword"
    const val API_UPDATE_ORDER = "update_order"
    const val API_ORDER_DETAILS = "order_details"

    // edit profile apis
    const val API_GET_STATE = "state"
    const val API_GET_CITY = "city"
    const val API_UPDATE_PROFILE = "updateProfile"
}