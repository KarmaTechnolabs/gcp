package com.gcptrack.utils

/**
 * Here in this class there are constants for the preference keys, argument keys and any other constant used in the
 * application except the api constants which resides in the APIConstants.
 *
 * The Preference and Arguments key's value type is appended in the key name itself.
 * If it is integer key contains _INT at end.
 * If it is boolean key contains _BOOL at end.
 * If it is float key contains _FLOAT at end.
 * If it is long key contains _LONG at end.
 * Other than this every single key's value is String data type.
 */
object Constants {
    const val PUSH_NOTIFICATION_CHANNEL_ID = "PUSH_NOTIFICATION_GCP"

    const val API_OFFSET_ITEM = 15
    const val DEFAULT_CURRENCY = "₹"

    //user type
    const val USER_CLIENT = "client"
    const val USER_ADMIN = "admin"

    //region Preference Constants
    const val PREF_FIREBASE_TOKEN = "PREF_FIREBASE_TOKEN"
    const val PREF_BEARER_TOKEN = "PREF_BEARER_TOKEN"
    const val PREF_UPDATE_SKIPPED_VERSION = "PREF_UPDATE_SKIPPED_VERSION"
    const val PREF_LOGIN_DATA = "PREF_LOGIN_DATA"
    const val PREF_IS_INTRODUCTION_FINISHED_BOOL = "PREF_IS_INTRODUCTION_FINISHED"
    //endregion

    //region EXTRAS
    const val EXTRA_ACTIVITY_RESULT_REQUEST_CODE = 101

    const val EXTRA_CATEGORY_TYPE = "CATEGORY_TYPE"
    const val EXTRA_TITLE = "WEB_TITLE"
    const val EXTRA_LINK = "WEB_LINK"

    const val EXTRA_TRACK_ORDER = "EXTRA_TRACK_ORDER"
    const val EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS"
    const val EXTRA_CUSTOMER = "EXTRA_CUSTOMER"
    const val EXTRA_DATA = "EXTRA_DATA"
    const val EXTRA_SELECTED_STATUS = "EXTRA_SELECTED_STATUS"
    const val EXTRA_SELECTED_STAGES = "EXTRA_SELECTED_STAGES"
    const val EXTRA_FILTER_TYPE = "EXTRA_FILTER_TYPE"

    const val KEY_FILE_NAME = "file_name"
    const val KEY_FILE_TYPE = "file_type"
    const val KEY_FILE_URI = "file_uri"
    const val KEY_FILE_URL = "file_url"

    const val CHANNEL_NAME = "Download File"
    const val CHANNEL_DESC = "Download file using work manager "
    const val CHANNEL_ID = "channel_100"
    const val NOTIFICATION_ID = 100
}