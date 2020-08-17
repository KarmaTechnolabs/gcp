package com.app.masterproject.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.app.masterproject.BuildConfig
import com.app.masterproject.R
import com.app.masterproject.api.responsemodel.LoginResponse
import com.app.masterproject.base.BaseFragment
import com.app.masterproject.custom.LogOutAlertDialog
import com.app.masterproject.custom.gotoActivity
import com.app.masterproject.custom.showToast
import com.app.masterproject.databinding.FragmentMoreNavigationBinding
import com.app.masterproject.ui.activities.*
import com.app.masterproject.utils.Constants
import com.app.masterproject.utils.UserStateManager
import com.app.masterproject.viewmodel.MoreViewModel

class MoreNavigationFragment : BaseFragment(), View.OnClickListener,
    LogOutAlertDialog.LogoutClickListener {

    private lateinit var binding: FragmentMoreNavigationBinding
    private val moreViewModel by viewModels<MoreViewModel>()
    private var isLogin = true
    private var loginResponseModel: LoginResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_more_navigation,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickListener = this

        val requireActivity = requireActivity()
        if (requireActivity is MainActivity)
            requireActivity.changeTheToolbar(false, getString(R.string.settings))

        isLogin = UserStateManager.isUserLoggedIn()
        loginResponseModel = UserStateManager.getUserProfile()
        binding.model = loginResponseModel

        isLogin = true
        binding.isLogin = isLogin
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.llEditProfile -> {
                if (isLogin) requireActivity().gotoActivity(
                    EditProfileActivity::class.java,
                    null,
                    false
                )
                else requireActivity().gotoActivity(LoginContainerActivity::class.java, null, false)
            }
            binding.tvChangePassword -> {
                requireActivity().gotoActivity(
                    ChangePasswordActivity::class.java,
                    null,
                    false
                )
            }


            binding.tvAboutUs-> {
                val bundle = bundleOf(
                    Constants.EXTRA_TITLE to getString(R.string.our_team),
                    Constants.EXTRA_LINK to BuildConfig.ABOUT_US
                )
                requireActivity().gotoActivity(
                    WebActivity::class.java,
                    bundle, false
                )
            }

            binding.tvLogout -> showLogOutDialog()
        }
    }

    private fun showLogOutDialog() {
        val logOutDialog =
            LogOutAlertDialog.newInstance(getString(R.string.are_you_sure_you_want_to_logout))
        logOutDialog.setListener(this)
        logOutDialog.show(childFragmentManager, "Log-out")
    }

    override fun onLogOutClick() {
        moreViewModel.callLogoutAPI.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(response) { _, message ->
                    showToast(message)
                    UserStateManager.logout(requireActivity())
                }
            }
        }
    }
}