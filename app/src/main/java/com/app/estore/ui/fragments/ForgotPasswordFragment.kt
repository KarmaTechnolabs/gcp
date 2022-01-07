package com.app.estore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.app.estore.R
import com.app.estore.api.requestmodel.ForgotPasswordRequestModel
import com.app.estore.base.BaseFragment
import com.app.estore.custom.showToast
import com.app.estore.databinding.FragmentForgotPasswordBinding
import com.app.estore.utils.Validator
import com.app.estore.viewmodel.OnBoardViewModel

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