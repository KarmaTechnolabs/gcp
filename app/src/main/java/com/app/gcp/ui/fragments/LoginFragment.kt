package com.app.gcp.ui.fragments

import android.annotation.SuppressLint
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
import com.app.gcp.api.requestmodel.LoginRequestModel
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.gotoActivity
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.FragmentLoginBinding
import com.app.gcp.ui.activities.DashboardActivity
import com.app.gcp.utils.UserStateManager
import com.app.gcp.utils.Validator
import com.app.gcp.viewmodel.OnBoardViewModel

class LoginFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        onBoardViewModel.loginResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { it, message ->
                    showToast(message)
                    UserStateManager.saveUserProfile(it)
                    requireActivity().gotoActivity(
                        DashboardActivity::class.java,
                        clearAllActivity = true
                    )
                }
            }
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun initView() {
        binding.lifecycleOwner = activity
        binding.clickListener = this

        binding.tieEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPrimary
                    )
                )
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPrimary
                    )
                )
            } else {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorGray
                    )
                )
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorGray
                    )
                )
            }
        }
        binding.tiePassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewPassword.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPrimary
                    )
                )
                binding.tiePassword.compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorPrimary
                    )
                )
            } else {
                binding.viewPassword.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorGray
                    )
                )
                binding.tiePassword.compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.colorGray
                    )
                )
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvForgotPassword -> findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())

            binding.btnTrackOrder -> findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTrackOrderFragment())

            binding.btnLogin -> {
                if (isDataValid()) {
                    val email = binding.tieEmail.text
                    val password = binding.tiePassword.text
                    onBoardViewModel.callLoginAPI(
                        LoginRequestModel(
                            email.toString(),
                            password.toString()/*,
                            getPreferenceValue(Constants.PREF_FIREBASE_TOKEN, "")*/
                        )
                    )
                }

//                requireActivity().gotoActivity(
//                    DashboardActivity::class.java,
//                    clearAllActivity = true
//                )

            }

        }


    }

    private fun isDataValid(): Boolean {
        val email = binding.tieEmail.text
        val password = binding.tiePassword.text

        binding.tieEmail.error = null
        binding.tiPassword.error = null

        return when {
            !Validator.isEmailValid(email) -> {
                showToast(R.string.enter_valid_email)
                binding.tieEmail.error = getString(R.string.enter_valid_email)
                false
            }
            TextUtils.isEmpty(password) -> {
                showToast(R.string.password_error)
                binding.tiePassword.error = getString(R.string.password_error)
                false
            }
            password?.length!! < 8 -> {//!Validator.isPasswordValid(password)
                showToast(R.string.enter_valid_password)
                binding.tiePassword.error = getString(R.string.enter_valid_password)
                false
            }
            else -> {
                true
            }
        }
    }
}