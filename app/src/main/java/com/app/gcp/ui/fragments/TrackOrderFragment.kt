package com.app.gcp.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.app.gcp.R
import com.app.gcp.api.requestmodel.ForgotPasswordRequestModel
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.FragmentForgotPasswordBinding
import com.app.gcp.databinding.FragmentTrackOrderBinding
import com.app.gcp.ui.dialogs.LogOutAlertDialog
import com.app.gcp.ui.dialogs.PasswordResetLinkAlertDialog
import com.app.gcp.utils.Validator
import com.app.gcp.viewmodel.OnBoardViewModel

class TrackOrderFragment : BaseFragment(), View.OnClickListener,
    PasswordResetLinkAlertDialog.ResetClickListener {

    private lateinit var binding: FragmentTrackOrderBinding
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clickListener = this

        onBoardViewModel.forgotPasswordResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    showToast(message)
                    findNavController().navigateUp()
                }
            }
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back, R.id.tv_back_to_login -> {
                findNavController().navigateUp()
            }
            R.id.btn_track_order -> {
                if (isDataValid()) {
//                    val email = binding.tieEmail.text
                    findNavController().navigateUp()
//                    onBoardViewModel.callForgotPasswordAPI(
//                        ForgotPasswordRequestModel(
//                            email.toString()
//                        )
//                    )
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        val trackingNumber = binding.tieTrackingNumber.text

        binding.tieTrackingNumber.error = ""

        return when {
            TextUtils.isEmpty(trackingNumber) -> {
                showToast(R.string.password_error)
                binding.tieTrackingNumber.error = getString(R.string.track_number_error)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onClick() {
        binding.tvBackToLogin.performClick()
    }
}