package com.app.gcp.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.app.gcp.api.responsemodel.LoginResponse
import com.app.gcp.base.BaseApplication
import com.app.gcp.custom.gotoActivity
import com.app.gcp.ui.activities.LoginContainerActivity
import com.google.gson.GsonBuilder

object UserStateManager {

    private val context: Context = BaseApplication.getApplicationContext()
    private val sharedPreferenceHelper: SharedPreferenceHelper
            = SharedPreferenceHelper.getInstance(context)

    fun isOnBoardingComplete(): Boolean {
        return sharedPreferenceHelper
            .getValue(Constants.PREF_IS_INTRODUCTION_FINISHED_BOOL, false)
    }

    fun isUserLoggedIn(): Boolean {
        return !TextUtils.isEmpty(
            sharedPreferenceHelper.getValue(Constants.PREF_LOGIN_DATA, "")
        )
    }

    fun getUserProfile(): LoginResponse? {
        val loginResponseJson = sharedPreferenceHelper.getValue(Constants.PREF_LOGIN_DATA,"")
        if (loginResponseJson.isNotEmpty())
            return Utils.getGSONWithExpose()?.fromJson<LoginResponse>(loginResponseJson,
                LoginResponse::class.java)
        return null
    }

    fun getFirebaseToken(): String {
        return sharedPreferenceHelper.getValue(Constants.PREF_FIREBASE_TOKEN, "")
    }

    fun getBearerToken(): String {
        return sharedPreferenceHelper.getValue(Constants.PREF_BEARER_TOKEN, "")
    }

    fun saveUserProfile(loginResponse: LoginResponse) {
        val gson =
            GsonBuilder().addSerializationExclusionStrategy(SerializedExclusionStrategy()).create()
        val json = gson.toJson(loginResponse)
        sharedPreferenceHelper.setValue(Constants.PREF_LOGIN_DATA, json)
        saveBearerToken(loginResponse.token)
    }

    fun markOnBoardingComplete() {
        sharedPreferenceHelper.setValue(Constants.PREF_IS_INTRODUCTION_FINISHED_BOOL, true)
    }
    fun saveFirebaseToken(token: String) {
        sharedPreferenceHelper.setValue(Constants.PREF_FIREBASE_TOKEN, token)
    }

    fun saveBearerToken(token: String) {
        sharedPreferenceHelper.setValue(Constants.PREF_BEARER_TOKEN, token)
    }

    fun logout(activity: Activity) {
        sharedPreferenceHelper.clearExcept(Constants.PREF_FIREBASE_TOKEN)
        sharedPreferenceHelper.setValue(Constants.PREF_LOGIN_DATA, "")
        activity.gotoActivity(
                LoginContainerActivity::class.java,
            clearAllActivity = true
        )
    }
}