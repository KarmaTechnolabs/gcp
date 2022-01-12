package com.app.gcp.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.app.gcp.R
import com.app.gcp.databinding.DialogPasswordResetLinkBinding

class PasswordResetLinkAlertDialog : DialogFragment(), View.OnClickListener {

    private lateinit var binding: DialogPasswordResetLinkBinding
    private var listener: ResetClickListener? = null

    companion object {

        fun newInstance(): PasswordResetLinkAlertDialog {
            val f = PasswordResetLinkAlertDialog()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        dialog?.setCancelable(false)

        if (dialog != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog()?.window!!.setWindowAnimations(R.style.dialog_animation_fade)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_password_reset_link, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackToLogin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        dismiss()
        if (view?.id == binding.btnBackToLogin.id) {
            listener?.onClick()
        }
    }

    interface ResetClickListener {
        fun onClick()
    }

    fun setListener(listener: ResetClickListener) {
        this.listener = listener
    }
}