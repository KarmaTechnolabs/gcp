package com.app.masterproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.app.masterproject.R
import com.app.masterproject.api.requestmodel.ForgotPasswordRequestModel
import com.app.masterproject.base.BaseFragment
import com.app.masterproject.custom.showToast
import com.app.masterproject.databinding.FragmentForgotPasswordBinding
import com.app.masterproject.utils.Validator
import com.app.masterproject.viewmodel.OnBoardViewModel

class ForgotPasswordFragment : BaseFragment(), View.OnClickListener {

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
        binding.clickListener = this

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
            R.id.iv_back -> {
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
//                showToast(R.string.enter_valid_email)
                binding.tiEmail.error = getString(R.string.enter_valid_email)
                false
            }
            else -> {
                true
            }
        }
    }
}