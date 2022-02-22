package com.app.gcp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.gcp.R
import com.app.gcp.base.BaseFragment
import com.app.gcp.custom.gotoActivity
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.FragmentTrackOrderBinding
import com.app.gcp.ui.activities.MainActivity
import com.app.gcp.ui.activities.OrderStatusActivity
import com.app.gcp.ui.dialogs.PasswordResetLinkAlertDialog
import com.app.gcp.viewmodel.OnBoardViewModel

class TrackOrderFragment : BaseFragment(), View.OnClickListener,
    PasswordResetLinkAlertDialog.ResetClickListener {

    private var _binding: FragmentTrackOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val onBoardViewModel by activityViewModels<OnBoardViewModel>()
    private val args: TrackOrderFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clickListener = this
        binding.isBack = args.isBack
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
//                    findNavController().navigateUp()
                    requireActivity().gotoActivity(
                        OrderStatusActivity::class.java,
                        clearAllActivity = false,
                        needToFinish = false
                    )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}