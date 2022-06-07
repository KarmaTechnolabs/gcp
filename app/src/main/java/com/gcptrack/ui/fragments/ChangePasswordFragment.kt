package com.gcptrack.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gcptrack.BuildConfig
import com.gcptrack.R
import com.gcptrack.api.requestmodel.ChangePasswordRequestModel
import com.gcptrack.api.responsemodel.LoginResponse
import com.gcptrack.base.BaseFragment
import com.gcptrack.custom.showToast
import com.gcptrack.databinding.FragmentChangePasswordBinding
import com.gcptrack.utils.UserStateManager
import com.gcptrack.viewmodel.DashBoardViewModel

class ChangePasswordFragment : BaseFragment(), View.OnClickListener {

    private val viewModel by activityViewModels<DashBoardViewModel>()
    private var _binding: FragmentChangePasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.clickListener = this
        if (BuildConfig.IS_DEBUG) {
            binding.tieNewPassword.setText("admin@gmail.com")
            binding.tieConfirmPassword.setText("admin@gmail.com")
        }

        viewModel.changePasswordResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    showToast(message)
                    var user: LoginResponse? = UserStateManager.getUserProfile()
                    user?.isPasswordChange = "1"
                    if (user != null) {
                        UserStateManager.saveUserProfile(user)
                    }
                    findNavController().navigateUp()
                }
            }
        }

    }

    override fun onClick(view: View?) {
        when (view) {

            binding.btnChangePassword -> {
                if (isDataValid()) {
                    val newPassword = binding.tieNewPassword.text.toString()
                    val changePasswordRequestModel =
                        ChangePasswordRequestModel(
                            user_type = UserStateManager.getUserProfile()?.user_type,
                            password = newPassword,
                            token = UserStateManager.getUserProfile()?.auth_token
                        )
                    viewModel.callChangePasswordAPI(changePasswordRequestModel)
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        val newPassword = binding.tieNewPassword.text
        val confirmPassword = binding.tieConfirmPassword.text

        return when {
            newPassword?.length!! < 8 -> {//!Validator.isPasswordValid(password)
                showToast(R.string.enter_valid_password)
//                binding.tieNewPassword.error = getString(R.string.enter_valid_password)
                false
            }
            confirmPassword.toString() != newPassword.toString() -> {
                showToast(R.string.new_password_confirm_password_does_not_match)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}