package com.gcptrack.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.gcptrack.BuildConfig
import com.gcptrack.R
import com.gcptrack.api.requestmodel.ForgotPasswordRequestModel
import com.gcptrack.base.BaseFragment
import com.gcptrack.custom.showToast
import com.gcptrack.databinding.FragmentForgotPasswordBinding
import com.gcptrack.ui.dialogs.PasswordResetLinkAlertDialog
import com.gcptrack.utils.Validator
import com.gcptrack.viewmodel.OnBoardViewModel

class ForgotPasswordFragment : BaseFragment(), View.OnClickListener,
    PasswordResetLinkAlertDialog.ResetClickListener {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.clickListener = this

        if (BuildConfig.IS_DEBUG) {
            binding.tieEmail.setText("admin@gmail.com")
        }

        onBoardViewModel.forgotPasswordResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    val resetPasswordDialog =
                        PasswordResetLinkAlertDialog.newInstance()
                    resetPasswordDialog.setListener(this)
                    resetPasswordDialog.show(childFragmentManager, "Reset-Password")
//                    showToast(message)
//                    findNavController().navigateUp()
                }
            }
        }

        binding.tieEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
            } else {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
            }
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back, R.id.tv_back_to_login -> {
                findNavController().navigateUp()
            }
            R.id.btn_send_link -> {
                if (isDataValid()) {
                    val email = binding.tieEmail.text
                    onBoardViewModel.callForgotPasswordAPI(
                        ForgotPasswordRequestModel(
                            email.toString()
                        )
                    )
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        val email = binding.tieEmail.text

        binding.tiEmail.error = ""

        return when {
            !Validator.isEmailValid(email) -> {
                showToast(R.string.enter_valid_email)
                binding.tieEmail.error = getString(R.string.enter_valid_email)
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