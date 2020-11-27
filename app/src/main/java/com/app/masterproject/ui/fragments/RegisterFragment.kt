package com.app.masterproject.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
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
import com.app.masterproject.R
import com.app.masterproject.api.requestmodel.RegisterRequestModel
import com.app.masterproject.base.BaseFragment
import com.app.masterproject.custom.checkIsNullOrBlank
import com.app.masterproject.custom.createClickableSpan
import com.app.masterproject.custom.showToast
import com.app.masterproject.databinding.FragmentRegisterBinding
import com.app.masterproject.utils.Validator
import com.app.masterproject.viewmodel.OnBoardViewModel

class RegisterFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        onBoardViewModel.registerResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    showToast(message)
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun initView() {
        binding.clickListener = this
        val customFontColorSpan =
            SpannableStringBuilder(getString(R.string.already_have_an_account))

        val registerSpan = getString(R.string.login).createClickableSpan {
            findNavController().navigateUp()
        }

        registerSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.colorGreen)), 0, getString(R.string.login).length, 0)
        registerSpan.setSpan(StyleSpan(Typeface.BOLD), 0, getString(R.string.login).length, 0)
        registerSpan.setSpan(StyleSpan(Typeface.ITALIC), 0, getString(R.string.login).length, 0)

        binding.tvLogin.text = customFontColorSpan.append(" ").append(registerSpan)
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()

        val registerConditionSpan =
            SpannableStringBuilder(getString(R.string.by_clicking_register_button_you_agree))

        val termsSpan = getString(R.string.terms_and_condition).createClickableSpan {

        }
        termsSpan.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            getString(R.string.terms_and_condition).length,
            0
        )

        termsSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.colorGreen)), 0, getString(R.string.terms_and_condition).length, 0)
        termsSpan.setSpan(StyleSpan(Typeface.BOLD), 0, getString(R.string.terms_and_condition).length, 0)
        termsSpan.setSpan(StyleSpan(Typeface.ITALIC), 0, getString(R.string.terms_and_condition).length, 0)


        val privacySpan = getString(R.string.privacy_policy).createClickableSpan {

        }
        privacySpan.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            getString(R.string.privacy_policy).length,
            0
        )

        privacySpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.colorGreen)), 0, getString(R.string.privacy_policy).length, 0)
        privacySpan.setSpan(StyleSpan(Typeface.BOLD), 0, getString(R.string.privacy_policy).length, 0)
        privacySpan.setSpan(StyleSpan(Typeface.ITALIC), 0, getString(R.string.privacy_policy).length, 0)


        binding.tvTermsAndPrivacy.text =
            registerConditionSpan.append(" ").append(termsSpan).append(" and ").append(privacySpan)
        binding.tvTermsAndPrivacy.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                if (isDataValid()) {
                    val firstName = binding.tieFirstName.text
                    val email = binding.tieEmail.text
                    val password = binding.tiePassword.text
                    onBoardViewModel.callRegisterAPI(
                        RegisterRequestModel(
                            firstName.toString(),
                            email.toString(),
                            password.toString()
                        )
                    )
                }
            }
            R.id.iv_back -> {
                findNavController().navigateUp()
            }
        }
    }

    private fun isDataValid(): Boolean {
        val firstName = binding.tieFirstName.text
        val lastName = binding.tieLastName.text
        val email = binding.tieEmail.text
        val password = binding.tiePassword.text
        val confirmPassword = binding.tieConfirmPassword.text

        binding.tiFirstName.error = ""
        binding.tiLastName.error = ""
        binding.tiEmail.error = ""
        binding.tiPassword.error = ""

        return when {
            firstName.checkIsNullOrBlank() -> {
//                showToast(R.string.enter_valid_first_name)
                binding.tiFirstName.error = getString(R.string.enter_valid_first_name)
                false
            }
            lastName.checkIsNullOrBlank() -> {
//                showToast(R.string.validation_last_name)
                binding.tiLastName.error = getString(R.string.validation_last_name)
                false
            }
            !Validator.isEmailValid(email) -> {
//                showToast(R.string.enter_valid_email)
                binding.tiEmail.error = getString(R.string.enter_valid_email)
                false
            }
            !Validator.isPasswordValid(password) -> {
//                showToast(R.string.enter_valid_password)
                binding.tiPassword.error = getString(R.string.enter_valid_password)
                false
            }
            confirmPassword.toString() != password.toString() -> {
//                showToast(R.string.password_confirm_password_does_not_match)
                binding.tiPassword.error = getString(R.string.password_confirm_password_does_not_match)
                false
            }
            else -> {
                true
            }
        }
    }
}