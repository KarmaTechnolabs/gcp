package com.gcptrack.ui.fragments

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
import com.gcptrack.BuildConfig
import com.gcptrack.R
import com.gcptrack.api.requestmodel.LoginRequestModel
import com.gcptrack.base.BaseFragment
import com.gcptrack.custom.gotoActivity
import com.gcptrack.custom.showToast
import com.gcptrack.databinding.FragmentLoginBinding
import com.gcptrack.ui.activities.DashboardActivity
import com.gcptrack.ui.activities.OtherServicesActivity
import com.gcptrack.utils.UserStateManager
import com.gcptrack.utils.Validator
import com.gcptrack.viewmodel.OnBoardViewModel

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
        if (BuildConfig.IS_DEBUG) {
//            binding.tieEmail.setText("jaydave8866@gmail.com")
//            binding.tiePassword.setText("123456789")
            binding.tieEmail.setText("admin@gmail.com")
            binding.tiePassword.setText("admin@gmail.com")
        }

        onBoardViewModel.loginResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { it, message ->
//                    showToast(message)
                    UserStateManager.saveUserProfile(it)
                    requireActivity().gotoActivity(
                        DashboardActivity::class.java,
//                        bundle = bundleOf(Constants.EXTRA_DATA to  UserStateManager.getUserProfile()?.user_type),
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

            binding.tvOurProduct -> requireActivity().gotoActivity(
                OtherServicesActivity::class.java,
                needToFinish = false
            )

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
//            password?.length!! < 8 -> {//!Validator.isPasswordValid(password)
//                showToast(R.string.enter_valid_password)
//                binding.tiePassword.error = getString(R.string.enter_valid_password)
//                false
//            }
            else -> {
                true
            }
        }
    }
}