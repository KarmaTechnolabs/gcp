package com.app.masterproject.api

object APIConstants {
    const val SUCCESS = 1
    const val FAILURE = 0

    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_BEARER = "Bearer "

    //OnBoarding APIs
    const val API_SIGN_IN = "login"
    const val API_REGISTER = "register"
    const val API_FORGOT_PASSWORD = "forgotPassword"
    const val API_LOGOUT = "logout"
    const val API_CHANGE_PASSWORD = "changePassword"

    // edit profile apis
    const val API_GET_STATE = "state"
    const val API_GET_CITY = "city"
    const val API_UPDATE_PROFILE = "updateProfile"
}