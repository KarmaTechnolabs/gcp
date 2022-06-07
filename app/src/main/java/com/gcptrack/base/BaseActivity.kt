package com.gcptrack.base

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import com.gcptrack.R
import com.gcptrack.custom.showToast
import com.gcptrack.listeners.PermissionListener


open class BaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null

    fun showProgressDialog() {
        fun showProgressDialog() {
            if (progressDialog == null || !progressDialog?.isShowing!!) {
                progressDialog = Dialog(this)
                progressDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                progressDialog?.setContentView(R.layout.custom_progressbar)
                progressDialog?.setCancelable(false)
                progressDialog?.show()
            }
        }
    }

    fun hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog?.isShowing!!) {
                progressDialog?.dismiss()
            }
        } catch (throwable: Throwable) {

        } finally {
            progressDialog?.dismiss()
        }
    }

    fun <T> manageAPIResource(resource: APIResource<T>, successListener: (T, String) -> Unit) {
        when (resource.status) {
            Status.LOADING -> {
                showProgressDialog()
            }
            Status.ERROR -> {
                hideProgressDialog()
                resource.message?.let {
                    showToast(it)
                }
            }
            Status.NO_NETWORK -> {
                hideProgressDialog()
                showToast(R.string.no_network_available)
            }
            else -> {
                hideProgressDialog()
                successListener.invoke(resource.data!!, resource.message!!)
            }
        }
    }

    fun <T> manageAPIResource(
        resource: APIResource<T>,
        isShowProgress: Boolean = true,
        successListener: (T, String) -> Unit,
        failureListener: () -> Unit
    ) {
        when (resource.status) {
            Status.LOADING -> {
                if (isShowProgress)
                    showProgressDialog()
            }
            Status.ERROR -> {
                hideProgressDialog()
                if (resource.message.equals(
                        resources.getString(R.string.no_record_found),
                        ignoreCase = true
                    )
                )
                    return
                resource.message?.let {
                    showToast(it)
                    failureListener.invoke()
                }
            }
            Status.NO_NETWORK -> {
                hideProgressDialog()
                showToast(R.string.no_network_available)
                failureListener.invoke()
            }
            else -> {
                hideProgressDialog()
                successListener.invoke(resource.data!!, resource.message!!)
            }
        }
    }

    fun showPermissionAlert(message: String, listener: PermissionListener) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.need_permission))
        builder.setMessage(message)
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { _, _ -> listener.onPermissionClick() }
        builder.setNeutralButton(getString(R.string.cancel), null)
        val dialog = builder.create()
        dialog.show()
    }
}