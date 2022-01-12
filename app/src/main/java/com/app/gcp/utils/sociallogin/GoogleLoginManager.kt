package com.app.gcp.utils.sociallogin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.gcp.BuildConfig
import com.app.gcp.R
import com.app.gcp.utils.showInfoAlert
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleLoginManager {
    private var fragment: Fragment? = null
    private var activity: AppCompatActivity? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private val REQUEST_GOOGLE_SIGN_IN = 1000
    private var fragmentManager: FragmentManager
    private var googleLoginListener: GoogleLoginListener? = null

    constructor(activity: AppCompatActivity) {
        this.activity = activity
        fragmentManager = activity.supportFragmentManager
        init()
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        fragmentManager = fragment.childFragmentManager
        init()
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_LOGIN_CLIENT_KEY)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        if (activity != null) {
            googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        } else if (fragment != null && fragment?.activity != null) {
            googleSignInClient = GoogleSignIn.getClient(fragment!!.requireActivity(), gso)
        }
    }

    fun login(googleLoginListener: GoogleLoginListener?) {
        this.googleLoginListener = googleLoginListener
        val signInIntent = googleSignInClient?.signInIntent
        if (activity != null) {
            activity?.startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN)
        } else if (fragment != null) {
            fragment?.startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            googleSignInClient?.signOut()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            //If the login succeed
            val account = task.getResult(ApiException::class.java)
            if (checkIfAlive() && googleLoginListener != null && account != null) {
                googleLoginListener?.onGoogleLoginSuccess(account.idToken)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
            if (e.statusCode != GoogleSignInStatusCodes.SIGN_IN_CANCELLED
                && e.statusCode != ConnectionResult.CANCELED
            ) {
                val context = activity ?: fragment?.context
                val errorTitle = context?.getString(R.string.login_failed) ?: ""
                val errorMessage = context?.getString(R.string.google_login_failed) ?: ""
                if (activity != null) {
                    activity?.showInfoAlert(errorTitle, errorMessage)
                } else if (fragment != null) {
                    fragment?.showInfoAlert(errorTitle, errorMessage)
                }
            }
        }
    }

    /**
     * @return true if fragment/activity is visible
     */
    private fun checkIfAlive(): Boolean {
        var activity: Activity? = activity
        if (fragment != null) {
            activity = fragment!!.activity
        }
        return activity != null &&
            !activity.isFinishing &&
            !activity.isDestroyed
    }

    fun interface GoogleLoginListener {
        fun onGoogleLoginSuccess(accessToken: String?)
    }
}