package com.app.gcp.ui.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.app.gcp.R
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.createClickableSpan
import com.app.gcp.custom.gotoActivity
import com.app.gcp.custom.savePreferenceValue
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.FragmentLoginBinding
import com.app.gcp.ui.activities.DashboardActivity
import com.app.gcp.ui.activities.MainActivity
import com.app.gcp.utils.Constants
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
                    savePreferenceValue(Constants.PREF_USER_ID_INT, it.id)
                    savePreferenceValue(Constants.PREF_USER_SLUG, it.userSlug)
                    savePreferenceValue(Constants.PREF_USER_FIRST_NAME, it.firstName)
                    savePreferenceValue(Constants.PREF_USER_MIDDLE_NAME, it.middleName)
                    savePreferenceValue(Constants.PREF_USER_LAST_NAME, it.lastName)
                    savePreferenceValue(Constants.PREF_USER_EMAIL, it.email)
                    savePreferenceValue(Constants.PREF_USER_PHONE_NUMBER, it.userMobile)
                    savePreferenceValue(Constants.PREF_USER_PROFILE_IMAGE, it.profileImage)
                    requireActivity().gotoActivity(
                        MainActivity::class.java,
                        clearAllActivity = true
                    )
                }
            }
        }
    }

    private fun initView() {
        binding.clickListener = this
        val customFontColorSpan = SpannableStringBuilder(getString(R.string.don_t_have_an_account))
        val registerSpan = getString(R.string.register).createClickableSpan {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        registerSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.colorPrimary)), 0, getString(R.string.register).length, 0)
        registerSpan.setSpan(StyleSpan(Typeface.BOLD), 0, getString(R.string.register).length, 0)
        registerSpan.setSpan(StyleSpan(Typeface.ITALIC), 0, getString(R.string.register).length, 0)

        binding.tieEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
            } else {
                binding.viewEmail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
                binding.tieEmail.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
            }
        }
        binding.tiePassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewPassword.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
                binding.tiePassword.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))
            } else {
                binding.viewPassword.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
                binding.tiePassword.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.colorGray))
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
//                    onBoardViewModel.callLoginAPI(
//                        LoginRequestModel(
//                            email.toString(),
//                            password.toString(),
//                            getPreferenceValue(Constants.PREF_FIREBASE_TOKEN, "")
//                        )
//                    )
                }

                requireActivity().gotoActivity(
                    DashboardActivity::class.java,
                    clearAllActivity = true
                )

            }

        }


    }

    private fun isDataValid(): Boolean {
        return true
        val email = binding.tieEmail.text
        val password = binding.tiePassword.text

        binding.tieEmail.error = ""
        binding.tiPassword.error = ""

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

            !Validator.isPasswordValid(password) -> {
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