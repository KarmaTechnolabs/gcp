package com.app.gcp.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.app.gcp.R
import com.app.gcp.custom.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.progressindicator.LinearProgressIndicator

open class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.peekHeight = bottomSheet.height
        }
    }

    fun <T> manageAPIResource(
        resource: APIResource<T>,
        progressIndicator: LinearProgressIndicator? = null,
        errorListener: ((T?, String?) -> Unit)? = null,
        needToShowErrorMessage: Boolean = true,
        successListener: (T, String) -> Unit
    ) {
        when (resource.status) {
            Status.LOADING -> {
                progressIndicator?.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                progressIndicator?.hide()
                if (needToShowErrorMessage)
                    resource.message?.let {
                        showToast(it)
                    }
                errorListener?.invoke(resource.data, resource.message)
            }
            Status.NO_NETWORK -> {
                progressIndicator?.hide()
                showToast(R.string.no_network_available)
            }
            else -> {
                progressIndicator?.hide()
                successListener.invoke(resource.data!!, resource.message!!)
            }
        }
    }
}
