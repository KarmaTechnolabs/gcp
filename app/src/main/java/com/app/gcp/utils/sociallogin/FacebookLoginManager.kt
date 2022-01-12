package com.app.gcp.utils.sociallogin

import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.facebook.FacebookException
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.gcp.R
import com.app.gcp.utils.showInfoAlert

class FacebookLoginManager {
    private var activity: AppCompatActivity? = null
    private var fragment: Fragment? = null
    private var listener: FacebookLoginListener? = null
    private val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }
    private var fragmentManager: FragmentManager?
    private var permissions = listOf("email", "public_profile")

    constructor(activity: AppCompatActivity) {
        this.activity = activity
        fragmentManager = activity.supportFragmentManager
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        fragmentManager = fragment.childFragmentManager
    }


    fun login(listener: FacebookLoginListener?) {
        this.listener = listener
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    if (listener != null) {
                        if (checkIfAlive()) {
                            listener.onFacebookLoginSuccess(loginResult.accessToken.token)
                        }
                    }
                }

                override fun onCancel() {}
                override fun onError(exception: FacebookException) {
                    if (listener != null) {
                        if (checkIfAlive()) {
                            val context = activity ?: fragment?.context
                            val errorTitle = context?.getString(R.string.login_failed) ?: ""
                            val errorMessage = context?.getString(R.string.fb_login_failed) ?: ""
                            if (activity != null) {
                                activity?.showInfoAlert(errorTitle, errorMessage)
                            } else if (fragment != null) {
                                fragment?.showInfoAlert(errorTitle, errorMessage)
                            }
                        }
                    }
                }
            })
        if (fragment != null) {
            LoginManager.getInstance().logInWithReadPermissions(fragment, permissions)
        } else if (activity != null) {
            LoginManager.getInstance().logInWithReadPermissions(activity, permissions)
        }
    }

    /**
     * @return true if fragment/activity is visible
     */
    private fun checkIfAlive(): Boolean {
        var activity: Activity? = activity
        if (fragment != null) {
            activity = fragment?.activity
        }
        return activity != null &&
            !activity.isFinishing &&
            !activity.isDestroyed
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun interface FacebookLoginListener {
        fun onFacebookLoginSuccess(accessToken: String?)
    }
}