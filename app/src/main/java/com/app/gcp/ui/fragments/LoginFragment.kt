package com.app.gcp.ui.fragments

import android.content.Intent
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
import com.app.gcp.ui.activities.MainActivity
import com.app.gcp.utils.Constants
import com.app.gcp.utils.sociallogin.FacebookLoginManager
import com.app.gcp.utils.UserStateManager
import com.app.gcp.utils.Validator
import com.app.gcp.utils.sociallogin.GoogleLoginManager
import com.app.gcp.viewmodel.OnBoardViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class LoginFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()
    private lateinit var facebookLoginManager: FacebookLoginManager
    private lateinit var googleLoginManager: GoogleLoginManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        facebookLoginManager = FacebookLoginManager(this)
        googleLoginManager = GoogleLoginManager(this)

        initView()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                if (!token.isNullOrEmpty())
                    UserStateManager.saveFirebaseToken(token)
            })

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
        binding.tvRegister.text = customFontColorSpan.append(" ").append(registerSpan)
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.tvForgotPassword -> findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())

            binding.btnLogin -> {
                /*if (isDataValid()) {
                    val email = binding.tieEmail.text
                    val password = binding.tiePassword.text
                    onBoardViewModel.callLoginAPI(
                        LoginRequestModel(
                            email.toString(),
                            password.toString(),
                            getPreferenceValue(Constants.PREF_FIREBASE_TOKEN, "")
                        )
                    )
                }*/

                requireActivity().gotoActivity(
                    MainActivity::class.java,
                    clearAllActivity = true
                )

            }
            binding.fbLogin -> {
                facebookLoginManager.login { accessToken ->
                    //TODO - Call Login API with FB Access Token
                }
            }
            binding.googleLogin -> {
                googleLoginManager.login { accessToken ->
                    //TODO - Call Login API with Google Access Token
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookLoginManager.onActivityResult(requestCode, resultCode, data)
        googleLoginManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun isDataValid(): Boolean {
        val email = binding.tiePhone.text
        val password = binding.tiePassword.text

        binding.tiePhone.error = ""
        binding.tiPassword.error = ""

        return when {
            !Validator.isEmailValid(email) -> {
//                showToast(R.string.enter_valid_email)
                binding.tiePhone.error = getString(R.string.enter_valid_phone)
                false
            }
            TextUtils.isEmpty(password) -> {
//                showToast(R.string.password_error)
                binding.tiPassword.error = getString(R.string.password_error)
                false
            }

            !Validator.isPasswordValid(password) -> {
//                showToast(R.string.enter_valid_password)
                binding.tiPassword.error = getString(R.string.enter_valid_password)
                false
            }
            else -> {
                true
            }
        }
    }
}