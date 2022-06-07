package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.gcptrack.R
import com.gcptrack.api.requestmodel.ChangePasswordRequestModel
import com.gcptrack.api.responsemodel.LoginResponse
import com.gcptrack.base.BaseActivity
import com.gcptrack.custom.showToast
import com.gcptrack.databinding.ActivityChangePasswordBinding
import com.gcptrack.utils.UserStateManager
import com.gcptrack.utils.Validator
import com.gcptrack.viewmodel.ChangePasswordViewModel

class ChangePasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        initView()

        viewModel.changePasswordResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    showToast(message)
                    var user: LoginResponse? = UserStateManager.getUserProfile()
                    user?.isPasswordChange = "1"
                    if (user != null) {
                        UserStateManager.saveUserProfile(user)
                    }
                    onBackPressed()
                }
            }
        }
    }

    private fun initView() {
        binding.toolbarChangePassword.tvTitle.text = getString(R.string.change_password)
        binding.toolbarChangePassword.ivBack.setOnClickListener(this)
        binding.btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> onBackPressed()
            R.id.btn_change_password -> {
                if (isDataValid()) {
                    val oldPassword = binding.tieOldPassword.text.toString()
                    val newPassword = binding.tieNewPassword.text.toString()
                    val changePasswordRequestModel =
                        ChangePasswordRequestModel(oldPassword, newPassword, newPassword)
                    viewModel.callChangePasswordAPI(changePasswordRequestModel)
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        val oldPassword = binding.tieOldPassword.text
        val newPassword = binding.tieNewPassword.text
        val confirmPassword = binding.tieConfirmPassword.text

        return when {
            !Validator.isPasswordValid(oldPassword) -> {
                showToast(R.string.enter_valid_password)
                false
            }
            !Validator.isPasswordValid(newPassword) -> {
                showToast(R.string.enter_valid_password)
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
}